package thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author zyl
 * @date 2018年6月28日
 * @desc 并发线程控制
 */
public class SemaphoreTest {
	
	//控制test()方法最大并发线程数为5
	private static Semaphore semaphore = new Semaphore(5, true);

	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					test();
				}
			}).start();
		}
	}
	private static void test() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println(Thread.currentThread().getName()+" 进来了。");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		semaphore.release();
	}
}
