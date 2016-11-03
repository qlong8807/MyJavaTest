package thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	public static void main(String[] args) {

		final Task task = new Task();
		/**
		 * 线程1，输出A
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i < 10; i++) {

					task.outputA();
				}
			}
		}, "线程1，输出A").start();

		/**
		 * 线程2，输出B
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i < 10; i++) {

					task.outputB();
				}

			}
		}, "线程2，输出B").start();

		/**
		 * 线程3，输出C
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i < 10; i++) {

					task.outputC();
				}

			}
		}, "线程3，输出C").start();

		/**
		 * 线程4，输出D
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i < 10; i++) {

					task.outputD();
				}

			}
		}, "线程4，输出D").start();
	}

	public static class Task {

		private Lock lock = new ReentrantLock();
		private BufferedWriter bw1 = null;
		private BufferedWriter bw2 = null;
		private BufferedWriter bw3 = null;
		private BufferedWriter bw4 = null;
		// 用于控制每一轮输出，对于每一轮，每个线程都要分别向不同的文件中输入相应的内容
		private int ctl = 0;
		/**
		 * 以下四个condition，用于控制四个线程的同步！
		 */
		private Condition cond1 = lock.newCondition();
		private Condition cond2 = lock.newCondition();
		private Condition cond3 = lock.newCondition();
		private Condition cond4 = lock.newCondition();
		/**
		 * 用于控制，每一轮必须所有的线程工作完毕后，才开始下一轮
		 */
		private boolean[] outputThisRound = { false, true, true, true };

		public Task() {

			try {

				// 可以将内容输出的bin目录中
				/*
				 * URL fileFolderPath = Test.class.getClassLoader().getResource(
				 * "com/test/play"); bw1 = new BufferedWriter( new
				 * OutputStreamWriter(new FileOutputStream(
				 * fileFolderPath.getFile() + "file1.txt"))); bw2 = new
				 * BufferedWriter( new OutputStreamWriter(new FileOutputStream(
				 * fileFolderPath.getFile() + "file2.txt"))); bw3 = new
				 * BufferedWriter( new OutputStreamWriter(new FileOutputStream(
				 * fileFolderPath.getFile() + "file3.txt"))); bw4 = new
				 * BufferedWriter( new OutputStreamWriter(new FileOutputStream(
				 * fileFolderPath.getFile() + "file4.txt")));
				 */

				// 将内容输出到 D 盘 A B C D 四个文件中
				bw1 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(new File("D:/A.txt"))));
				bw2 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(new File("D:/B.txt"))));
				bw3 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(new File("D:/C.txt"))));
				bw4 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(new File("D:/D.txt"))));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void outputA() {

			lock.lock();

			try {

				while (outputThisRound[0]) {

					System.out.println("outputA begin to await!");
					cond1.await();
				}

				if (ctl % 4 == 0) {

					bw1.write("A");
					bw1.flush();
				} else if (ctl % 4 == 1) {

					bw4.write("A");
					bw4.flush();
				} else if (ctl % 4 == 2) {

					bw3.write("A");
					bw3.flush();
				} else if (ctl % 4 == 3) {

					bw2.write("A");
					bw2.flush();
				}

				outputThisRound[0] = true;
				outputThisRound[1] = false;

				System.out.println("outputA signal outputB!");
				cond2.signal();

			} catch (Exception e) {

				e.printStackTrace();

			} finally {

				lock.unlock();
			}

		}

		public void outputB() {

			lock.lock();

			try {

				while (outputThisRound[1]) {

					System.out.println("outputB begin to await!");
					cond2.await();
				}

				if (ctl % 4 == 0) {

					bw2.write("B");
					bw2.flush();
				} else if (ctl % 4 == 1) {

					bw1.write("B");
					bw1.flush();
				} else if (ctl % 4 == 2) {

					bw4.write("B");
					bw4.flush();
				} else if (ctl % 4 == 3) {

					bw3.write("B");
					bw3.flush();
				}

				outputThisRound[1] = true;
				outputThisRound[2] = false;
				System.out.println("outputB signal outputC!");
				cond3.signal();

			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				lock.unlock();
			}

		}

		public void outputC() {

			lock.lock();

			try {

				while (outputThisRound[2]) {

					System.out.println("outputC begin to await!");
					cond3.await();
				}

				if (ctl % 4 == 0) {

					bw3.write("C");
					bw3.flush();
				} else if (ctl % 4 == 1) {

					bw2.write("C");
					bw2.flush();
				} else if (ctl % 4 == 2) {

					bw1.write("C");
					bw1.flush();
				} else if (ctl % 4 == 3) {

					bw4.write("C");
					bw4.flush();
				}

				outputThisRound[2] = true;
				outputThisRound[3] = false;
				System.out.println("outputC signal outputD!");
				cond4.signal();

			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				lock.unlock();
			}

		}

		public void outputD() {

			lock.lock();
			try {
				while (outputThisRound[3]) {

					System.out.println("outputD begin to await!");
					cond4.await();
				}
				if (ctl % 4 == 0) {

					bw4.write("D");
					bw4.flush();
				} else if (ctl % 4 == 1) {

					bw3.write("D");
					bw3.flush();
				} else if (ctl % 4 == 2) {

					bw2.write("D");
					bw2.flush();
				} else if (ctl % 4 == 3) {

					bw1.write("D");
					bw1.flush();
				}

				outputThisRound[3] = true;
				outputThisRound[0] = false;
				ctl++;
				System.out.println("outputD signal outputA!");
				cond1.signal();

			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				lock.unlock();
			}

		}

	}

}
