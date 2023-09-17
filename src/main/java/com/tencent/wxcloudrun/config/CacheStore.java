package com.tencent.wxcloudrun.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
//test

import java.util.concurrent.TimeUnit;

public class CacheStore<T> {
    private final Cache<String, T> cache;

    public CacheStore(int expiry) {
        cache = CacheBuilder.newBuilder().expireAfterWrite(expiry, TimeUnit.SECONDS).concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
    }

    public T get(String key) {
        return cache.getIfPresent(key);
    }

    public void add(String key, T value) {
        if (key != null && value != null) {
            cache.put(key, value);
        }
    }
}
