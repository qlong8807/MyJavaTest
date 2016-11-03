/**
 * 
 */
package defineAnnotation.test;

import defineAnnotation.anno.AnnotationMy;

/**
 * @author zyl
 * @date 2016年11月3日
 * 
 */
@AnnotationMy
public class Student implements Person {
    
    private String stuNo;

    @Override
    @AnnotationMy(name="流氓公子") //赋值给name  默认的为张三
//在定义注解时没有给定默认值时，在此处必须name赋初值
    public void name() {
        
    }


    @Override
    @AnnotationMy(say=" hello java ！")
    public void say() {
        
    }

    @Override
    @AnnotationMy(name="李四",say="i am lisi",age=20)
    public void age() {
        
    }
}