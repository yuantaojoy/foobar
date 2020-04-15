package spring.framework.core.ioc.denpendency;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author 曲元涛
 * @date 2020/4/12 17:31
 */
@Component
@Scope(scopeName = "prototype")
public class MovieFinder {
    private String str;

    public MovieFinder(String str) {
        this.str = str;
    }

    public void handler() {
        System.out.println(this + ", " + str);
    }
}
