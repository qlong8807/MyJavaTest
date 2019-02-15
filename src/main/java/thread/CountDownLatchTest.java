package thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zyl
 * @date 2019年1月21日
 * @desc CountDownLatch 叫倒计数，允许一个或多个线程等待某些操作完成。
 * 
 * 跑步比赛，裁判需要等到所有的运动员（“其他线程”）都跑到终点（达到目标），才能去算排名和颁奖。
 */
public class CountDownLatchTest {
	
	private CountDownLatch countDownLatch = new CountDownLatch(5);

	public static void main(String[] args) {
		CountDownLatchTest test = new CountDownLatchTest();
		test.begin();
	}
	private void begin() {
		Random random = new Random();
		for(int i=0;i<5;i++) {
			int dist = random.nextInt(3)+1;
			new Thread(new Runner(dist)).start();
		}
		try {
			countDownLatch.await(5, TimeUnit.SECONDS);
			System.out.println("全部跑完了");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class Runner implements Runnable{
		int distance;//跑步距离
		private Runner(int dist) {
			this.distance = dist;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(distance*1000);
				System.out.println(Thread.currentThread().getName()+" 跑完了 "+distance);
				countDownLatch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
