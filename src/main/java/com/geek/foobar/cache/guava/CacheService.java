package com.geek.foobar.cache.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

/**
 * @author 曲元涛
 * @date 2020/4/2 20:31
 */
@Component
public class CacheService {

    public Cache<String, String> guavaCache() {
        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        cache.put("guava", "hello guava cache");
        return cache;
    }
}
