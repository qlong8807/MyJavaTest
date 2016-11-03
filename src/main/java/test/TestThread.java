package test;

import java.util.Random;

public class TestThread implements Runnable{
	//�����ֲ߳̾�����studentLocal���ں�����ᷢ����������Student����
    private final static ThreadLocal studentLocal = new ThreadLocal();
	public static void main(String[] args) {
		TestThread tt = new TestThread();
		Thread t1 = new Thread(tt, "ts1");
		Thread t2 = new Thread(tt, "ts2");
		t1.start();
		t2.start();
	}
	 protected Student getStudent() {
	        //��ȡ�����̱߳�����ǿ��ת��ΪStudent����
	        Student student = (Student) studentLocal.get();
	        //�߳��״�ִ�д˷�����ʱ��studentLocal.get()�϶�Ϊnull
	        if (student == null) {
	            //����һ��Student���󣬲����浽�����̱߳���studentLocal��
	            student = new Student();
	            studentLocal.set(student);
	        }
	        return student;
	    }

	@Override
	public void run() {
		//��ȡ��ǰ�̵߳�����
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("thread " + currentThreadName + " is running!");

        //����һ�����������ӡ
        Random random = new Random();
        int age = random.nextInt(100);
        System.out.println("thread " + currentThreadName + " set age to:" + age);

        //��ȡһ��Student���󣬲��������������뵽����������
        Student student = getStudent();
        student.setAge(age);
        System.out.println("thread " + currentThreadName + " first read age is:" + student.getAge());
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("thread " + currentThreadName + " second read age is:" + student.getAge());
		
	}
	private class Student{
		int age;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
		
	}
}
