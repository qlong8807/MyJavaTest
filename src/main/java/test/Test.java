package test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test {
	public static volatile int i = 1;

	public static synchronized void increase() {
		i++;
	}

	private static List<String> list = new ArrayList<String>();

	public static void save(String string) {
		System.err.println("hello");
		list.add(string);
		synchronized (list) {
			if (list.size() > 100) {
				System.err.println(list.size());
				list.clear();
			}
		}
	}

	public static void main(String[] args) {
		try {
			Class[] methodParamTypes = getMethodParamTypes(
					Test.class.newInstance(), "save");
			System.out.println(methodParamTypes[0]);
			Method m = Test.class.newInstance().getClass()
					.getDeclaredMethod("save", methodParamTypes);
			m.invoke(Test.class, "aaa");
			System.out.println(m.getParameterTypes().getClass().getCanonicalName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Class[] getMethodParamTypes(Object classInstance,
			String methodName) throws ClassNotFoundException {
		Class[] paramTypes = null;
		Method[] methods = classInstance.getClass().getMethods();// 全部方法
		for (int i = 0; i < methods.length; i++) {
			if (methodName.equals(methods[i].getName())) {// 和传入方法名匹配
				Class[] params = methods[i].getParameterTypes();
				paramTypes = new Class[params.length];
				for (int j = 0; j < params.length; j++) {
					System.out.println(params[j].getName());
					paramTypes[j] = Class.forName(params[j].getName());
				}
				break;
			}
		}
		return paramTypes;
	}

}
