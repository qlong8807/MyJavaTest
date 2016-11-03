package timer;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestTimer {
	public static void main(String[] args) {
		start();
		// start();
	}
	public static void start() {
		System.out.println(System.currentTimeMillis() / 1000);
		long SECOND = 1000;
		HeartbeatTask heartbeatTask = new HeartbeatTask();
		PrintSome printSome = new PrintSome();
//		Timer timer = new Timer();
//		timer.schedule(heartbeatTask, 1 * SECOND, 2 * SECOND);
		ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(5);
		scheduExec.scheduleWithFixedDelay(heartbeatTask, SECOND * 0, SECOND * 2,
				TimeUnit.MILLISECONDS);
		scheduExec.scheduleWithFixedDelay(printSome, SECOND * 1, SECOND * 2,
				TimeUnit.MILLISECONDS);
		System.out.println("---	" + System.currentTimeMillis() / 1000);
	}
}

class HeartbeatTask extends TimerTask {
	int i=0;
	@Override
	public void run() {
		i++;
		System.out.println(i+"=============" + System.currentTimeMillis() / 1000);
		if(i%5==0){
			try {
//				throw new RuntimeException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
class PrintSome extends TimerTask {
	@Override
	public void run() {
			try {
				System.out.println("-------------" + System.currentTimeMillis() / 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
