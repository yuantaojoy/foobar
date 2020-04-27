package annotation.repeatable;

import java.lang.reflect.Method;

/**
 * @author 曲元涛
 * @date 2020/4/27 14:53
 */
@Schedule(dayOfMonth = "last")
@Schedule(dayOfWeak = "Tuesday", hour = 24)
public class RepeatableTest {

    @Schedule(dayOfMonth = "last")
    @Schedule(dayOfWeak = "Friday", hour = 15)
    public void doPeriodCleanUp() {}

    public static void main(String[] args) throws NoSuchMethodException {

        Method doPeriodCleanUp = RepeatableTest.class.getDeclaredMethod("doPeriodCleanUp");

        Schedules schedules = doPeriodCleanUp.getAnnotation(Schedules.class);
        System.out.println("获取标记方法上的重复注解：");
        for (Schedule schedule : schedules.value()) {
            System.out.println(schedule);
        }

        System.out.println("获取标记类上的重复注解：");
        if (RepeatableTest.class.isAnnotationPresent(Schedules.class)) {
            schedules = RepeatableTest.class.getAnnotation(Schedules.class);
            for (Schedule schedule : schedules.value()) {
                System.out.println(schedule);
            }
        }
    }
}
