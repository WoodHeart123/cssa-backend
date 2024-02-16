package org.cssa.wxcloudrun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = {"org.cssa.wxcloudrun.dao"})
@EnableScheduling
public class WxCloudRunApplication {


    public static void main(String[] args) {
        SpringApplication.run(WxCloudRunApplication.class, args);
    }

}
