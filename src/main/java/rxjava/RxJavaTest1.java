package rxjava;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxJavaTest1 {

	@Test
	public void hello() {
		String[] names = new String[] { "abc", "jones", "stenve" };
		Observable.fromArray(names).subscribe(new Consumer<String>() {

			@Override
			public void accept(String arg0) throws Exception {
				System.out.println(arg0);
			}
		});
	}

	/**
	 * single是一个Observable
	 */
	@Test
	public void single() {
		Single<Long> single = Single.just(1l);

		single.subscribe(new SingleObserver<Long>() {
			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("sub:" + d);
			}

			@Override
			public void onSuccess(Long value) {
				// 和onNext是一样的
				System.out.println("success:" + value);
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void test1() {
		// 创建一个被观察者
		Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				emitter.onNext(2);
				// emitter.onError(new IOException());
				emitter.onNext(3);
				emitter.onComplete();
			}
		});
		// 创建一个订阅者Observer
		Observer<Integer> observer = new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("sub:" + d.toString());
			}

			@Override
			public void onNext(Integer t) {
				System.out.println("next:" + t);
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("err:" + e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("complete");
			}
		};
		// 绑定两者关系
		observable.subscribe(observer);
	}

	/**
	 * rx链式编程
	 */
	@Test
	public void test2() {
		// 创建一个被观察者
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				emitter.onNext(2);
				// emitter.onError(new IOException());
				emitter.onNext(3);
				// emitter.onError(new NullPointerException());
				emitter.onNext(4);
				emitter.onComplete();
			}
		})./** 绑定两者关系 */
				subscribe(
						/** 创建一个订阅者Observer */
						new Observer<Integer>() {
							/**
							 * 我们可以把它理解成两根管道之间的一个机关, 当调用它的dispose()方法时, 它就会将两根管道切断, 从而导致下游收不到事件. 注意:
							 * 调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件.
							 */
							Disposable disposable = null;

							@Override
							public void onSubscribe(Disposable d) {
								System.out.println("sub:" + d.toString());
								disposable = d;
							}

							@Override
							public void onNext(Integer t) {
								System.out.println("next:" + t);
								if (3 == t) {
									disposable.dispose();// 收到3后，中断接收，后面的将收不到了。
								}
							}

							@Override
							public void onError(Throwable e) {
								System.out.println("-------------");
								System.out.println("err:" + e.getMessage());
								e.printStackTrace();
							}

							@Override
							public void onComplete() {
								System.out.println("complete");
							}
						});
	}

}
