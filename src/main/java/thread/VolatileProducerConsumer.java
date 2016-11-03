package thread;

/**
 * 不使用volatile可能会造成死锁。（这里我试了多次，有没有volatile结果都一样）
 * 1、写线程生成一个值，并将 hasValue 设置为 true。但是只更新CPU缓存中的值，而不是主内存。
 * 2、读线程尝试消费一个值，但是它的缓存副本中 hasValue 被设置为 false，所以即使写线程生产了一个新的值，也不能被消费，因为读线程无法跳出睡眠循环（hasValue 的值为 false）。
 * 3、因为读线程不能消费新生成的值，所以写线程也不能继续，因为标识变量没有设置回 false，因此写线程阻塞在睡眠循环中。
 * 4、这样，就产生了死锁！
 * @author zyl
 * @date 2015年12月25日
 */
public class VolatileProducerConsumer {
	private String value = "";
	private boolean hasValue = false;

	public void produce(String value) {
		while (hasValue) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Producing " + value + " as the next consumable");
		this.value = value;
		hasValue = true;
	}

	public String consume() {
		while (!hasValue) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String value = this.value;
		hasValue = false;
		System.out.println("Consumed " + value);
		return value;
	}
	
	public static void main(String[] args) {
		final VolatileProducerConsumer consumer = new VolatileProducerConsumer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<10;i++)
				consumer.produce(""+i);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<10;i++)
				consumer.consume();
			}
		}).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}