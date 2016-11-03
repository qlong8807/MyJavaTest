package designModel.dynamicProxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TestCglib2 {

	public static void main(String[] args) {
		final BookFacadeImpl1 p = new BookFacadeImpl1();
		// 返回p的动态代理对象:该代理对象是Person的子类
		BookFacadeImpl1 pp = (BookFacadeImpl1) Enhancer.create(p.getClass(),
				new MethodInterceptor() {
					@Override
					public Object intercept(Object proxy, Method method,
							Object[] arg2, MethodProxy arg3) throws Throwable {
						long time = System.nanoTime();// 纳秒 1毫秒=1000纳秒
						Object obj = method.invoke(p, arg2);
						System.out.println(method.getName() + "运行耗时："
								+ (System.nanoTime() - time) + "纳秒");
						return obj;
					}
				});
		pp.addBook1();
	}

}