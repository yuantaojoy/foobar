package com.geek.foobar.guava.retry;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author 曲元涛
 * @date 2020/4/7 10:43
 */
public class MyRetryTest {
    Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
            .retryIfException()
            .withStopStrategy(StopStrategies.stopAfterAttempt(5))
            .build();

    @Test
    public void test() {
        try {
            retryer.call(this.retryTest());
        } catch (ExecutionException | RetryException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private Callable<Void> retryTest() {
        return (Callable<Void>) () -> {
            for (int i = 0; i < 6; i++) {
                throw new RuntimeException("123");
            }
            return null;
        };
    }
}
