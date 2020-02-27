package db.springTempProc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface OracleDataType {
    int order() default 1;

    String dateFormat() default "yyyyMMddHHmm";

    String arrayDesc() default "";

    String structDesc() default "";
}
