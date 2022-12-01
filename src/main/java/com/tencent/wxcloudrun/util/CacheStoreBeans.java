package com.tencent.wxcloudrun.util;

import com.tencent.wxcloudrun.model.CacheStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Integer> authCodeCache() {
        return new CacheStore<Integer>(300);
    }

}