package com.gitee.whzzone.admin.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(false); // 不携带身份信息（Cookies）
//        corsConfiguration.addAllowedOriginPattern("*"); // 允许所有来源 （和下面二选一即可）
        corsConfiguration.addAllowedOrigin("*"); // 允许所有来源
        corsConfiguration.addAllowedHeader("*"); // 允许所有HTTP标头
        corsConfiguration.addAllowedMethod("*");  // 允许所有HTTP方法

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}