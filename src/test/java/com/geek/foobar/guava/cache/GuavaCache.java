package com.geek.foobar.guava.cache;

import com.google.common.cache.*;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
/**
 * @author 曲元涛
 * @date 2020/4/2 20:19
 */
public class GuavaCache {

    @Test
    public void testCache() {
        Cache<String, String> guavaCache = CacheBuilder.newBuilder().build();
        guavaCache.put("guava", "hello guava cache");
        assertNotNull(guavaCache.getIfPresent("guava"));
    }

    @Test
    public void testMaxCache() {
        Cache<String, String> guavaCache = CacheBuilder.newBuilder()
                .maximumSize(2).build();
        guavaCache.put("k1", "v1");
        guavaCache.put("k2", "v2");
        assertNotNull(guavaCache.getIfPresent("k1"));
        assertNotNull(guavaCache.getIfPresent("k2"));
        guavaCache.put("k3", "v3");
        assertNull(guavaCache.getIfPresent("k1"));
        assertNotNull(guavaCache.getIfPresent("k2"));
        assertNotNull(guavaCache.getIfPresent("k3"));
    }

    /**
     * 写入的N秒后失效
     *
     * @author 曲元涛
     * @date 2020/4/3 00:15
     */
    @Test
    public void testExpireAfterWrite() throws InterruptedException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build();
        cache.put("k1", "v1");
        int count = 1;
        do {
            System.out.printf("第%s次取到k1的值是%s\n", count++, cache.getIfPresent("k1"));
            Thread.sleep(1000L);
        } while (count < 5);
    }

    /**
     * 读取后N秒后失效
     *
     * @author 曲元涛
     * @date 2020/4/3 00:15
     */
    @Test
    public void testExpireAfterAccess() throws InterruptedException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .build();
        cache.put("k1", "v1");
        int count = 1;
        do {
            Thread.sleep(count * 1000L);
            System.out.printf("第%s次取到k1的值是%s\n", count++, cache.getIfPresent("k1"));
        } while (count < 5);
    }

    /**
     * 弱引用 key value
     *
     * @author 曲元涛
     * @date 2020/4/3 00:14
     */
    @Test
    public void testWeakValues() {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .weakValues()
                .build();
        Object value = new Object();
        cache.put("k1", value);
        value = new Object();
        System.gc();
        assertNull(cache.getIfPresent("k1"));
    }

    /**
     * 显式清除
     *
     * @author 曲元涛
     * @date 2020/4/3 00:14
     */
    @Test
    public void testInvalidate() {
        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        List<String> list = new ArrayList<>(Arrays.asList("k1", "k2"));
        cache.invalidateAll(list);
        assertNull(cache.getIfPresent("k1"));
        assertNull(cache.getIfPresent("k2"));
        assertNotNull(cache.getIfPresent("k3"));
    }

    /**
     * 移除监听器，记录被删除时可以感知到这个事件
     */
    @Test
    public void testRemovalListener() {
        RemovalListener<String, String> listener = notification -> {
            System.out.printf("[%s:%s] is removed !\n", notification.getKey(), notification.getValue());
        };
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build();
        for (int i = 0; i < 10; i++) {
            cache.put("k"+i, "v" + i);
        }
    }

    /**
     * 自动加载 必须在 main 方法里执行结果才符合预期
     *
     * @author 曲元涛
     * @date 2020/4/3 00:25
     */
    public void testGetAutoLoad() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .build();
        new Thread(() -> {
            System.out.println("thread-1");
            try {
                String value = cache.get("key", () -> {
                    System.out.println("load1");
                    Thread.sleep(1000L);
                    return "Auto Load From thread-1";
                });
                System.out.println("thread1 " + value);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            System.out.println("thread-2");
            try {
                String value = cache.get("key", () -> {
                    System.out.println("load2");
                    Thread.sleep(1000L);
                    return "Auto Load From thread2";
                });
                System.out.println("thread2 " + value);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 统计信息查看缓存命中率
     *
     * @author 曲元涛
     * @date 2020/4/3 00:41
     */
    @Test
    public void testRecordStats() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .recordStats() // 开启统计信息开关
                .build();
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        cache.put("k4", "v4");

        cache.getIfPresent("k1");
        cache.getIfPresent("k2");
        cache.getIfPresent("k3");
        cache.getIfPresent("k4");
        cache.getIfPresent("k5");
        cache.getIfPresent("k6");

        System.out.println(cache.stats());
    }

    @Test
    public void testLoadingCache() throws ExecutionException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                Thread.sleep(1000L);
                System.out.println(key + "is loaded from a cacheLoader");
                return key + "`s value";
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(3))
                .initialCapacity(32)
                .build(loader);
        System.out.println(cache.get("k1"));
        System.out.println(cache.get("k2"));
        System.out.println(cache.get("k3"));
        System.out.println(cache.asMap());
    }

    public static void main(String[] args) {
        GuavaCache cache = new GuavaCache();
        cache.testGetAutoLoad();
    }
}
