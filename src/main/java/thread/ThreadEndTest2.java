package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zyl
 * 使用线程池ExecutorService的terminal方法判断 线程是否全部结束
 */
public class ThreadEndTest2{
	public static void main(String[] args) throws Exception {
		int threadNum = 10;
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
//		newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
//		newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
//		newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
//		newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
		for(int i=0;i<threadNum;i++) {
			Thread t = new Thread2();
			executor.submit(t);
//			executor.execute(t);//等同于上面的方法
		}
		executor.shutdown();//不能再提交新任务，等待的不受影响。
		//判断方式一
//		boolean loop = true;
//		do {
//			loop = !executor.awaitTermination(200, TimeUnit.MILLISECONDS);
//		}while(loop);
		//判断方式二
		while(true) {
			if(executor.isTerminated()) {
				break;
			}
			Thread.sleep(200);
		}
		
		System.out.println("所有线程都已结束"+System.currentTimeMillis());
	}
}

class Thread2 extends Thread{
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行线程："+Thread.currentThread().getName()+"--"+System.currentTimeMillis());
	}
	
}