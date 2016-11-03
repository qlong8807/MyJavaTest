/**
 * 
 */
package defineAnnotation.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zyl
 * @date 2016年11月3日
 * @interface注解： 定义注解接口
@Target注解： 用于约束被描述的注解的使用范围，当被描述的注解超出使用范围则编译失败。如：ElementType.METHOD,ElementType.TYPE；
@Retention 注解：用于约束被定义注解的作用范围，作用范围有三个：
1,、RetentionPolicy.SOURCE:作用范围是源码，作用于Java文件中，当执行javac时去除该注解。
2、RetentionPolicy.CLASS：作用范围是二进制码，就是存在于class文件中，当执行Java时去除该注解。
3、RetentionPolicy.RUNTIME：作用范围为运行时，就是我们可以通过动态获取该注释。
@Documented：用于指定javadoc生成API文档时显示该注释。
@Inherited:用于指定被描述的注释可以被其描述的类的子类继承，默认情况是不能被其子类继承。
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationMy {
	String name() default "张三";
	String say() default "hello world";
	int age() default 20;
}
