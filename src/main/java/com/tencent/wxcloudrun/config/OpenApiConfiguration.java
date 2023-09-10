package com.tencent.wxcloudrun.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Strings;
import com.tencent.wxcloudrun.model.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

@Slf4j
@Configuration
@EnableOpenApi
public class OpenApiConfiguration {

    public static final String TOKEN = "token";
    public static final String WX_ID = "x-wx-openid";
    public static final String HEADER = "header";
    @Resource
    private SwaggerProperties swaggerProperties;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }


    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(apis())
                .paths(PathSelectors.any())
                .build()
                .enable(isEnable())
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .useDefaultResponseMessages(false)
                .directModelSubstitute(Timestamp.class, Date.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(com.tencent.wxcloudrun.model.Response.class, WildcardType.class),
                        typeResolver.resolve(WildcardType.class)
                ))
                .globalResponses(HttpMethod.GET, List.of(new ResponseBuilder().code("200").description("调用成功").build()))
                .globalResponses(HttpMethod.POST, List.of(new ResponseBuilder()
                        .code("200").description("调用成功").build()));
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

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> arrayList = new ArrayList<>();
        arrayList.add(new ApiKey(TOKEN, TOKEN, HEADER));
        arrayList.add(new ApiKey(WX_ID, WX_ID, HEADER));
        return arrayList;
    }

    private Predicate<RequestHandler> apis() {
        String basePackage = swaggerProperties.getBasePackage();
        // 默认通过扫描`ApiOperation`如果配置了包扫描路径，使用配置的包扫描路径
        return Strings.isNullOrEmpty(basePackage) ? withMethodAnnotation(ApiOperation.class) : basePackage(basePackage);
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextsList = new ArrayList<>();
        securityContextsList.add(SecurityContext.builder()
                .securityReferences(adminAuth())
                .operationSelector(
                        oc -> oc.requestMappingPattern().matches("/admin/.*")
                )
                .build());
        securityContextsList.add(SecurityContext.builder().
                securityReferences(userAuth())
                .operationSelector(
                        oc -> !oc.requestMappingPattern().matches("/admin/.*")
                ).build());
        return securityContextsList;
    }

    private List<SecurityReference> adminAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("admin api", "use to access admin interface");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> defaultAuthList = new ArrayList<>();
        defaultAuthList.add(new SecurityReference(TOKEN, authorizationScopes));
        return defaultAuthList;
    }

    private List<SecurityReference> userAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("user api", "use to access user interface");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> defaultAuthList = new ArrayList<>();
        defaultAuthList.add(new SecurityReference(WX_ID, authorizationScopes));
        return defaultAuthList;
    }
}
