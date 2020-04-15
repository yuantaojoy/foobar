package spring.framework.core.ioc.denpendency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * @author 曲元涛
 * @date 2020/4/12 17:31
 */
@Component
public class SimpleMovieListener{

    @Autowired
    MovieFinder movieFinder1;

    public void printMovie() {
        MovieFinder movieFinder = this.createMovieFinder("我是@Lookup构造出来的");
        movieFinder.handler();

        movieFinder1.handler();
    }

    @Lookup
    public MovieFinder createMovieFinder(String str) {
        return null;
    }
}
