package com.gitee.whzzone.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
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
    /**
     * 配置Swagger2相关的bean
     */
    @Bean
    public Docket docketDemo1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gitee.whzzone"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                ;
    }


    /**
     * 此处主要是API文档页面显示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("wander-server") // 标题
                .description("wander-server api") // 描述
                .termsOfServiceUrl("") // 服务网址，一般写公司地址
                .version("1.0") // 版本
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> result = new ArrayList<>();
        result.add(new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header"));
        return result;
    }
}
