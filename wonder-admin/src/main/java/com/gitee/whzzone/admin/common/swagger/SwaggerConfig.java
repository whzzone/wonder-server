package com.gitee.whzzone.admin.common.swagger;

import cn.dev33.satoken.config.SaTokenConfig;
import com.gitee.whzzone.admin.common.properties.WonderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/17 10:01
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Autowired
    private WonderProperties wonderProperties;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private AntPathMatcher antPathMatcher;

    @Autowired
    private SaTokenConfig saTokenConfig;

    /**
     * 配置Swagger2相关的bean
     */
    @Bean
    public Docket docketDemo1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gitee.whzzone.admin.system"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts()) // 单个Api: 某个api的调试中显示
                .securitySchemes(securitySchemes()) // 全局: 侧边栏显示
                .groupName("1、系统分组")
                ;
    }

    @Bean
    public Docket docketDemo2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gitee.whzzone.admin.business"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .groupName("2、业务分组")
                ;
    }

    /**
     * 此处主要是API文档页面显示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("wonder-server") // 标题
                .description("多部门用户需要在请求头中传递当前请求用户选择的deptId, 例如 DeptId: 32") // 描述
                .termsOfServiceUrl("") // 服务网址，一般写公司地址
                .version("1.0") // 版本
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> result = new ArrayList<>();
        result.add(new ApiKey(HttpHeaders.AUTHORIZATION, saTokenConfig.getTokenName(), "header"));
        result.add(new ApiKey("DeptId", "DeptId", "header"));
        return result;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        // 设置需要登录的认证路径
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o -> {
                            // 判断请求路径是否匹配ignore-path中的接口
                            String requestMappingPattern = o.requestMappingPattern();
                            for (String ignorePath : wonderProperties.getWeb().getIgnorePath()) {
                                if (antPathMatcher.match(contextPath + ignorePath, requestMappingPattern)) {
                                    // 如果匹配，则创建一个没有任何安全要求的SecurityContext
                                    return false;
                                }
                            }
                            return true;
                        })
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference(saTokenConfig.getTokenName(), authorizationScopes));
        securityReferences.add(new SecurityReference("DeptId", authorizationScopes));
        return securityReferences;
    }

}
