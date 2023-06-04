package com.tencent.wxcloudrun.config;

import com.google.common.base.Strings;
import com.tencent.wxcloudrun.model.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;

import java.util.function.Predicate;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

@Slf4j
@Configuration
@EnableOpenApi
public class OpenApiConfiguration {

    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(apis())
                .paths(PathSelectors.any())
                .build()
                .enable(isEnable());
    }

    private Boolean isEnable() {
        Boolean enable = swaggerProperties.getEnable();
        if (enable) {
            log.info("\nEnable Swagger3...");
        }
        return enable;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getServiceUrl())
                .version(swaggerProperties.getVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .build();
    }

    private Predicate<RequestHandler> apis() {
        String basePackage = swaggerProperties.getBasePackage();
        // 默认通过扫描`ApiOperation`如果配置了包扫描路径，使用配置的包扫描路径
        return Strings.isNullOrEmpty(basePackage) ? withMethodAnnotation(ApiOperation.class) : basePackage(basePackage);
    }

}
