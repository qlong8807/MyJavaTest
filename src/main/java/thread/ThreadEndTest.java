package thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author zyl
 * 通过CountDownLatch判断所有线程是否结束
 */
public class ThreadEndTest{
	public static void main(String[] args) throws Exception {
		int threadNum = 10;
		CountDownLatch countDownLatch = new CountDownLatch(threadNum);
		for(int i=0;i<threadNum;i++) {
			Thread t = new Thread1(countDownLatch);
			t.start();
		}
		countDownLatch.await();
		System.out.println("所有线程都已结束"+System.currentTimeMillis());
	}
}

class Thread1 extends Thread{
	private CountDownLatch countDownLatch;
	public Thread1(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行线程："+Thread.currentThread().getName()+"--"+System.currentTimeMillis());
		countDownLatch.countDown();
	}
	
}