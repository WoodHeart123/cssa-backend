package org.cssa.wxcloudrun.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@Hidden
@ConfigurationProperties(prefix = "cssa.wxcloudrun.swagger")
public class SwaggerProperties {
    /**
     * 是否swagger3启用，默认不启用
     */
    private Boolean enable = false;


    private String basePackage;
    /**
     * 标题
     */
    private String title;
    /**
     * 应用描述
     */
    private String description;
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 版本，默认V1.0.0
     */
    private String version = "V1.0.0";
    /**
     * license
     */
    private String license;
    /**
     * licenseUrl
     */
    private String licenseUrl;
}