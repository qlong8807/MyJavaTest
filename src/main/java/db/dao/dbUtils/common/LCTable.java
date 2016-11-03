package db.dao.dbUtils.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给mysql实体类添加注�?
 * 
 * @author ss
 * 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LCTable {

	/**
	 * @return 数据库表�?
	 */
	String name();
}
