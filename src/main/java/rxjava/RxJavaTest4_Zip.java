package rxjava;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class RxJavaTest4_Zip {
	/**
	 * 同一个线程执行。被观察者1发送完，才会一个个发送被观察者2
	 */
	@Test
	public void testZip() {
		Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> e) throws Exception {
				e.onNext(1);
				System.out.println(1);
				Thread.sleep(1000);
				e.onNext(2);
				System.out.println(2);
				Thread.sleep(1000);
				e.onNext(3);
				System.out.println(3);
				Thread.sleep(1000);
				e.onNext(4);
				System.out.println(4);
				Thread.sleep(1000);
				e.onComplete();
			}
		});
		Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				e.onNext("A");
				System.out.println("A");
				Thread.sleep(1000);
				e.onNext("B");
				System.out.println("B");
				Thread.sleep(1000);
				e.onNext("C");
				System.out.println("C");
				Thread.sleep(1000);
				e.onComplete();
			}
		});
		Observable.zip(observable1,observable2, new BiFunction<Integer, String, String>() {
			@Override
			public String apply(Integer t1, String t2) throws Exception {
				return t1+t2;
			}
		}).subscribe(new Observer<String>() {

			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("disposable");
			}

			@Override
			public void onNext(String t) {
				System.out.println("value:"+t);
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("complete");
			}
		});
	}
}
