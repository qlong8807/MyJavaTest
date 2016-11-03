package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinCountTask extends RecursiveTask<Integer> {

	private static final int THRESHOLD = 2;// 阀值
	private int start;
	private int end;

	public ForkJoinCountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		// 任务足够小
		boolean canCompare = (end - start) <= THRESHOLD;
		if (canCompare) {
			for (int i = start; i < end; i++) {
				sum += i;
				/*try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		} else {
			// 如果任务大于阀值，则拆分
			int middle = (start + end) / 2;
			ForkJoinCountTask leftTask = new ForkJoinCountTask(start, middle);
			ForkJoinCountTask rightTask = new ForkJoinCountTask(middle + 1, end);
			// 执行子任务
			leftTask.fork();
			rightTask.fork();
			// 等待子任务执行完，并得到结果
			int leftValue = leftTask.join();
			int rightValue = rightTask.join();

			sum = leftValue + rightValue;
		}
		return sum;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinCountTask task = new ForkJoinCountTask(1, 1000);
		ForkJoinTask<Integer> submit = pool.submit(task);
		try {
			System.err.println(submit.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.err.println(end-start);
		System.out.println(sum(1, 1000));
	}
	public static Integer sum(int start,int end){
		int sum = 0;
		for(int i=start;i<=end;i++){
			sum += i;
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		return sum;
	}
}
