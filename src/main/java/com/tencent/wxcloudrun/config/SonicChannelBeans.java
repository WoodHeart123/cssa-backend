package com.tencent.wxcloudrun.config;

import com.github.twohou.sonic.ChannelFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SonicChannelBeans {

    @Bean
    public ChannelFactory channelFactory() {
        return new ChannelFactory("47.97.183.103",1491,"SecretPassword",2000,2000);
    }
}
