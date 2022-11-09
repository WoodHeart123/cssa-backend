package com.tencent.wxcloudrun.config;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;

@Configuration
public class JedisConfig {

    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Bean
    public JedisPooled jedisPooled() {
        GenericObjectPoolConfig<Connection> jedisPoolConfig = new GenericObjectPoolConfig<Connection>();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        return new JedisPooled(jedisPoolConfig, host, port, timeout);
    }


}
