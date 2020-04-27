package annotation.repeatable;

import java.lang.annotation.Repeatable;

/**
 * @author 曲元涛
 * @date 2020/4/27 14:54
 */
@Repeatable(Schedules.class)
public @interface Schedule {

    String dayOfMonth() default "first";

    String dayOfWeak() default "Monday";

    int hour() default 12;
}
