package thread.java8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zyl
 * @date 2018年8月15日
 * @desc Executors必须显式的停止-否则它们将持续监听新的任 务。
 * ExecutorService 提供了两个方法来达到这个目的—— shutdwon() 会等待正在执行的任务执行完,
 * 而shutdownNow() 会终止所有正在执行的任务并立即关闭 executor。
 */
public class ExecutorThread1 {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		//一般用法
		executor.submit(() -> {
			String name = Thread.currentThread().getName();
			System.out.println("执行了run方法:"+name);
		});
		executor.submit(() -> {
			try {
				TimeUnit.SECONDS.sleep(60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name = Thread.currentThread().getName();
			System.out.println("执行了run2方法:"+name);
		});
		//最好这样关闭
		try {
			System.out.println("准备关闭executor");
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);//超时则强制关闭
		} catch (Exception e) {
			System.out.println("关闭时出错了");
		}finally {
			if(!executor.isTerminated()) {
				System.out.println("强制关闭");
				executor.shutdownNow();
			}
		}
	}
}
