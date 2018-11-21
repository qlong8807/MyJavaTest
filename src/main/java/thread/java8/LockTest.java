package thread.java8;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @author zyl
 * @date 2018年8月15日
 * @desc ReentrantLock,ReentrantReadWriteLock,
 */
public class LockTest {
	public static void main(String[] args) {
		//测试readWriteLock
//		readWriteLockTest();
		
		//Java8自带了一种新的锁，叫做 StampedLock，它同样支持读写锁，就像上面的例子那样。
		//与ReadWriteLock 不同的是，StampedLock的锁方法会返回表示为long的标记。
		//你可以使用这些标记来释放锁，或者检查锁是否有效。此外，StampedLock支持另一种叫做乐观锁(optimistic locking)的模式。
		stampedLockTest();
	}

	public static void readWriteLockTest() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		ReadWriteLock lock = new ReentrantReadWriteLock();
		// 写线程会阻塞住读线程
		// 写线程
		executor.submit(() -> {
			lock.writeLock().lock();
			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("writing "+Thread.currentThread().getName());
				map.put("foo", "bar");
			} finally {
				lock.writeLock().unlock();
			}
		});
		// 读线程
		Runnable readTask = () -> {
			lock.readLock().lock();
			try {
				System.out.println("read:" + map.get("foo")+" "+Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.readLock().unlock();
			}
		};

		executor.submit(readTask);
		executor.submit(readTask);

		stop(executor);
	}

	public static void stop(ExecutorService executor) {
		try {
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

	public static void stampedLockTest() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		StampedLock lock = new StampedLock();
		// 写线程会阻塞住读线程
		// 写线程
		executor.submit(() -> {
			long writeLock = lock.writeLock();
			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("writing "+Thread.currentThread().getName());
				map.put("foo", "bar");
			} finally {
				lock.unlockWrite(writeLock);
			}
		});
		// 读线程
		Runnable readTask = () -> {
			long readLock = lock.readLock();
			try {
				System.out.println("read:" + map.get("foo")+" "+Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.unlockRead(readLock);
			}
		};

		executor.submit(readTask);
		executor.submit(readTask);

		stop(executor);
	}
}
