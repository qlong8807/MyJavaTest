package thread.java8;

public class ThreadTest1 {
	public static void main(String[] args) {
		Runnable task = () ->{
			String name = Thread.currentThread().getName();
			System.out.println("执行了run方法:"+name);
		};
		task.run();
		Thread t1 = new Thread(task);
		t1.start();
		System.out.println("done!");
	}
}
