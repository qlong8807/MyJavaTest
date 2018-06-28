package rxjava;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * map一变多；
 * flatMap不按照原顺序；
 * concatMap严格按照原顺序；
 * @author zyl
 * @date 2017年12月22日
 * @desc 
 */
public class RxJavaTest3_Map {
	@Test
	public void testMap() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
			}
		}).map(new Function<Integer, String>() {
			@Override
			public String apply(Integer t) throws Exception {
				return "i am:" + t;
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				System.out.println(s);
			}
		});
	}

	@Test
	public void testFlatMap() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
			}
		}).flatMap(new Function<Integer, ObservableSource<String>>() {
			@Override
			public ObservableSource<String> apply(Integer integer) throws Exception {
				final List<String> list = new ArrayList<>();
				for (int i = 0; i < 3; i++) {
//					System.out.println(i);
					list.add("I am value " + i + "   " + integer);
				}
//				return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
				return Observable.fromIterable(list);
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				System.out.println(s);
			}
		},new Consumer<Throwable>() {
			@Override
			public void accept(Throwable t) throws Exception {
				t.printStackTrace();
			}
		});
	}
	@Test
	public void testConcatMap() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
			}
		}).concatMap(new Function<Integer, ObservableSource<String>>() {
			@Override
			public ObservableSource<String> apply(Integer integer) throws Exception {
				final List<String> list = new ArrayList<>();
				for (int i = 0; i < 3; i++) {
//					System.out.println(i);
					list.add("I am value " + i + "   " + integer);
				}
//				return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
				return Observable.fromIterable(list);
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				System.out.println(s);
			}
		},new Consumer<Throwable>() {
			@Override
			public void accept(Throwable t) throws Exception {
				t.printStackTrace();
			}
		});
	}
}
