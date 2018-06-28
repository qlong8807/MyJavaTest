package rxjava;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 在RxJava中, 已经内置了很多线程选项供我们选择, 例如有:
 * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作;
 * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作;
 * Schedulers.newThread() 代表一个常规的新线程;
 * AndroidSchedulers.mainThread() 代表Android的主线程;
 * 
 * 这些内置的Scheduler已经足够满足我们开发的需求, 因此我们应该使用内置的这些选项, 在RxJava内部使用的是线程池来维护这些线程,所有效率也比较高.
 * 
 * @author zyl
 * @date 2017年12月22日
 * @desc
 */
public class RxJavaTest2_Thread {

	@Test
	public void test1() {
		// 创建一个被观察者
		Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				System.out.println("observable:" + Thread.currentThread().getName());
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
				emitter.onComplete();
			}
		});
		// 创建一个订阅者Observer
		Consumer<Integer> consumer = new Consumer<Integer>() {
			@Override
			public void accept(Integer t) throws Exception {
				System.out.println("consumer:" + t + "   " + Thread.currentThread().getName());
			}
		};
		/**
		 * 简单的来说, subscribeOn() 指定的是上游发送事件的线程, observeOn() 指定的是下游接收事件的线程.
		 * 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
		 * 多次指定下游的线程是可以的,也就是说每调用一次observeOn() , 下游的线程就会切换一次.
		 */
		observable.subscribeOn(Schedulers.newThread())
				.observeOn(Schedulers.computation())
				.subscribe(consumer);
	}
}
