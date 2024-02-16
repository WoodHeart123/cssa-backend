package org.cssa.wxcloudrun.util;

import org.cssa.wxcloudrun.config.CacheStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Integer> authCodeCache() {
        return new CacheStore<Integer>(300);
    }

}