package thread.java8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zyl
 * @date 2018年6月28日
 * @desc 并发线程控制
 * 并发限制：CountDownLatch,Semaphore
 */
public class SemaphoreTest2 {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	//控制test()方法最大并发线程数为5
	private static Semaphore semaphore = new Semaphore(5, true);

	public static void main(String[] args) {
		Runnable task = () -> {
			test();
		};

		IntStream.range(0, 10).forEach(i -> executor.submit(task));
//		stop(executor);
	}
	private static void test() {
		boolean permit = false;
		try {
			permit = semaphore.tryAcquire(1,TimeUnit.SECONDS);//获取锁的等待时间
//			permit = semaphore.tryAcquire(6,TimeUnit.SECONDS);
			if(permit) {
				System.err.println("semaphore acquire:"+Thread.currentThread().getName());
				Thread.sleep(5000);
			}else {
				System.err.println("could not acquire semaphore");
			}
//			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(permit) {
				semaphore.release();
			}
		}
	}

	public static void stop(ExecutorService executor) {
		try {
			executor.shutdown();
//			executor.awaitTermination(5, TimeUnit.SECONDS);// 超时则强制关闭
		} catch (Exception e) {
			System.out.println("出错了");
		} finally {
			if (!executor.isTerminated()) {
				System.out.println("强制关闭");
				executor.shutdownNow();
			}
		}
	}
}
