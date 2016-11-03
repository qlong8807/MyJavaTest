/**
 * 
 */
package defineAnnotation.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import defineAnnotation.anno.AnnotationMy;

/**
 * @author zyl
 * @date 2016年11月3日
 * 
 */
public class AnnoTest {
	Annotation[] annotation = null;

	public static void main(String[] args) throws ClassNotFoundException {
		new AnnoTest().getAnnotation();
	}

	public void getAnnotation() throws ClassNotFoundException {
		Class<?> stu = Class.forName("defineAnnotation.test.Student");// 静态加载类
		boolean hasAnnotationMy = stu
				.isAnnotationPresent(defineAnnotation.anno.AnnotationMy.class);
		// 判断stu是不是使用了我们刚才定义的注解接口
		if (hasAnnotationMy) {
			annotation = stu.getAnnotations();// 获取注解接口中的
			for (Annotation a : annotation) {
				if (a instanceof AnnotationMy) {
					AnnotationMy my = (AnnotationMy) a;// 强制转换成AnnotationMy类型
					System.out.println(stu + ",name:" + my.name() + ",say: "
							+ my.say() + ",age: " + my.age());
				}
			}
		}
		Method[] method = stu.getMethods();
		System.out.println("Method");
		for (Method m : method) {
			boolean ismEmpty = m
					.isAnnotationPresent(defineAnnotation.anno.AnnotationMy.class);
			if (ismEmpty) {
				Annotation[] aa = m.getAnnotations();
				for (Annotation a : aa) {
					AnnotationMy an = (AnnotationMy) a;
					System.out.println(stu + ",name:" + an.name() + ",say: "
							+ an.say() + ",age: " + an.age());
				}
			}
		}
		// get Fields by force
		System.out.println("get Fileds by force :::");
		Field[] field = stu.getDeclaredFields();
		for (Field f : field) {
			f.setAccessible(true);
			System.out.println(f.getName());
		}
		System.out.println("get methods in interfaces :::");
		Class<?> interfaces[] = stu.getInterfaces();
		for (Class<?> c : interfaces) {
			Method[] imethod = c.getMethods();
			for (Method m : imethod) {
				System.out.println(m.getName());
			}
		}
	}
}
