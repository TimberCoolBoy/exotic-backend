package com.timber.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter getCorsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //添加哪些http方法可以跨域，比如：GET.POST（多个方法中间以逗号分隔），*号表示所有
        corsConfiguration.addAllowedMethod("*");
        //添加允许哪个请求进行跨域，*表示所有，可以具体指定http://localhost:8601表示只允许http://localhost:8601/跨域
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedOriginPattern("*");
        //所有头信息全部放行
        corsConfiguration.addAllowedHeader("*");
        //允许跨域发送cookie
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
