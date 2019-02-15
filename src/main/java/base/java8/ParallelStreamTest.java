package base.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

/**
 * @description parallelStream是并行的流。
 * 以下示例说明forEach方法用了一些小把戏。它会将执行forEach本身的线程也作为线程池中的一个工作线程。
 * 因此，即使将ForkJoinPool的通用线程池的线程数量设置为1，实际上也会有2个工作线程。
 * 因此在使用forEach的时候，线程数为1的ForkJoinPool通用线程池和线程数为2的ThreadPoolExecutor是等价的。
 * 所以当ForkJoinPool通用线程池实际需要4个工作线程时，可以将它设置成3，那么在运行时可用的工作线程就是4了。
 */
public class ParallelStreamTest {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
		// 构造一个10000个元素的集合
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			list.add(i);
		}
		// 统计并行执行list的线程
		Set<Thread> threadSet = new CopyOnWriteArraySet<>();
		// 并行执行
		list.parallelStream().forEach(integer -> {
			Thread thread = Thread.currentThread();
			// System.out.println(thread);
			// 统计并行执行list的线程
			threadSet.add(thread);
		});
		System.out.println("一个集合并行时用到的线程数，threadSet一共有" + threadSet.size() + "个线程");
		System.out.println("系统一个有" + Runtime.getRuntime().availableProcessors() + "个cpu");
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			list1.add(i);
			list2.add(i);
		}
		Set<Thread> threadSetTwo = new CopyOnWriteArraySet<>();
		CountDownLatch countDownLatch = new CountDownLatch(2);
		Thread threadA = new Thread(() -> {
			list1.parallelStream().forEach(integer -> {
				Thread thread = Thread.currentThread();
				threadSetTwo.add(thread);
			});
			countDownLatch.countDown();
		});
		Thread threadB = new Thread(() -> {
			list2.parallelStream().forEach(integer -> {
				Thread thread = Thread.currentThread();
				threadSetTwo.add(thread);
			});
			countDownLatch.countDown();
		});

		threadA.start();
		threadB.start();
		countDownLatch.await();
		System.out.println("两个线程中对两个集合进行并行计算，用到的线程数，threadSetTwo一共有" + threadSetTwo.size() + "个线程");

		System.out.println("---------------------------");
		System.out.println(threadSet);
		System.out.println(threadSetTwo);
		System.out.println("---------------------------");
		threadSetTwo.addAll(threadSet);
		System.out.println(threadSetTwo);
		System.out.println("threadSetTwo一共有" + threadSetTwo.size() + "个线程");
		System.out.println("系统一个有" + Runtime.getRuntime().availableProcessors() + "个cpu");
	}
}
