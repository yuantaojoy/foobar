package annotation.repeatable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author 曲元涛
 * @date 2020/4/27 14:57
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Schedules {
    Schedule[] value();
}
