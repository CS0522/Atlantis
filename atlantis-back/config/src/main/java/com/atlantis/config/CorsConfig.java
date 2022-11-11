package com.atlantis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    // 当前跨域请求最大有效时长。1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        System.out.println("doing CORS config...");
        // corsConfiguration.addAllowedOrigin("*"); // 设置访问源地址
        corsConfiguration.addAllowedOriginPattern("**");
//        corsConfiguration.addAllowedOrigin("http://localhost:8080/");
//        corsConfiguration.addAllowedOrigin("http://172.16.12.103:8080/");
        corsConfiguration.addAllowedHeader("*"); // 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 设置访问源请求方法
        corsConfiguration.setAllowCredentials(true); // 解决前后的session对象不一致问题

        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration); // 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
