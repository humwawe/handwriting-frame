package hum.annotation;

import java.lang.annotation.*;

/**
 * @author hum
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
