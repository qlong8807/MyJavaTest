package thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zyl
 * @date 2019年1月21日
 * @desc CyclicBarrier 叫循环栅栏，它实现让一组线程等待至某个状态之后再全部同时执行，
 * 而且当所有等待线程被释放后，CyclicBarrier 可以被重复使用。
 * CyclicBarrier 的典型应用场景是用来等待并发线程结束。
 * 
 * 场景示例：人到齐了才能开饭
 */
public class CyclicBarrierTest {
	
	private CyclicBarrier cyclicBarrier = new CyclicBarrier(3);//到3个人就开一桌
//	private CyclicBarrier cyclicBarrier = new CyclicBarrier(7);//所有人都到了才开饭

	public static void main(String[] args) {
		CyclicBarrierTest test = new CyclicBarrierTest();
		test.begin();
	}

	private void begin() {
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
			int dist = random.nextInt(3) + 1;
			new Thread(new Runner(dist)).start();
		}
		System.out.println("主线程结束");
	}

	private class Runner implements Runnable {
		int distance;// 跑步距离

		private Runner(int dist) {
			this.distance = dist;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(distance * 1000);
				System.out.println(Thread.currentThread().getName() + " 到达饭店 " + distance);
				int await = cyclicBarrier.await(10,TimeUnit.SECONDS);
				System.out.println(Thread.currentThread().getName() + " 开饭,等待时间" + await);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}

	}
}
