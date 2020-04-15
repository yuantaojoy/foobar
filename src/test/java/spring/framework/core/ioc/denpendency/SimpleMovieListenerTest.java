package spring.framework.core.ioc.denpendency;

import com.geek.foobar.FoobarApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @author 曲元涛
 * @date 2020/4/12 18:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoobarApplication.class)
@WebAppConfiguration
public class SimpleMovieListenerTest {

    @Resource
    private SimpleMovieListener simpleMovieListener;

    @Test
    public void test() {
        simpleMovieListener.printMovie();
        simpleMovieListener.printMovie();
    }
}
