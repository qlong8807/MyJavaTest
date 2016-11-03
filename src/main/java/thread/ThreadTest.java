package thread;

/**
 * 实现Runnable，没有start()方法。所以一般使用new Thread(new Runnable()).start();
 * 实现Runnable可以实现资源的共享。
 * @author zyl
 * @date 2015年12月24日
 */
public class ThreadTest {
	public static void main(String[] args) {
//		Mythread mt = new Mythread();
//		mt.start();
		
		MyRunnable mr = new MyRunnable();
		new Thread(mr).start();
		new Thread(mr).start();
	}
}
class Mythread extends Thread{
	private int ticket=10;
	public void run(){
		for(int i=0;i<20;i++){
			if(ticket > 0){
				System.out.println(Thread.currentThread().getName()+"卖出一张票，还剩"+(--ticket)+"张");
			}
		}
	}
}
class MyRunnable implements Runnable{
	private int ticket=10;
	@Override
	public void run() {
		for(int i=0;i<20;i++){
			if(ticket > 0){
				System.out.println(Thread.currentThread().getName()+"卖出一张票，还剩"+(--ticket)+"张");
			}
		}
	}
	
}