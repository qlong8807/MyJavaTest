package thread.java8;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author zyl
 * @date 2018年8月15日
 * @desc 除了 Runnable ，executor还支持另一种类型的任务—— Callable 。
 *       Callables也是类似于runnables的函数接口，不同之处在于，Callable返回一个值。
 */
public class ExecutorCallable2 {
	public static void main(String[] args) {
		//阻塞get.
//		test1();
		// Executors支持通过 invokeAll() 一次批量提交多个callable。这个方法结果一个
		// callable的集合，然后返回一个future的列表。
//		test2();

		// executor——调用 newWorkStealingPool() 。这个工厂方法是Java8引入的，返回一个ForkJoinPool类型的executor，
		// 它的工作方法与其他常见的execuotr稍有不 同。与使用一个固定大小的线程池不同， ForkJoinPools使用一个并行因子数来
		// 创建，默认值为主机CPU的可用核心数。
		//invokeAny
		test3();
	}

	public static void test1() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Callable<String> task = () -> {
			System.out.println("执行了Callable的call方法：" + Thread.currentThread().getName());
			Thread.sleep(2000);
			return "123";
		};
		Future<String> submit = executor.submit(task);
		try {
			System.out.println("done1:" + submit.isDone());
			System.out.println("done2:" + submit.isDone());
			String s1 = submit.get();// 调用get会阻塞住线程
			System.out.println("done3:" + submit.isDone());
			System.out.println("get1:" + s1);
			Thread.sleep(1000);
			String s2 = submit.get();
			System.out.println("get2:" + s2);
			System.out.println("准备关闭executor");
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);// 超时则强制关闭
		} catch (Exception e) {
			System.out.println("出错了");
		} finally {
			if (!executor.isTerminated()) {
				System.out.println("强制关闭");
				executor.shutdownNow();
			}
		}
	}

	public static void test2() {
		ExecutorService executor = Executors.newWorkStealingPool();
		List<Callable<String>> callables = Arrays.asList(() -> "task1", () -> "task2", () -> "task3");
		try {
			executor.invokeAll(callables).stream().map(future -> {
				try {
					return future.get();
				} catch (Exception e) {
					System.err.println("error");
				}
				return null;
			}).forEach(System.out::println);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void test3() {
		ExecutorService executor = Executors.newWorkStealingPool();
		List<Callable<String>> callables = Arrays.asList(callable("task1", 2), callable("task2", 1),
				callable("task3", 3));
		String result;
		try {
			result = executor.invokeAny(callables);
			System.out.println(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Callable<String> callable(String result, long sleepSeconds) {
		return () -> {
			TimeUnit.SECONDS.sleep(sleepSeconds);
			return result;
		};
	}
}
