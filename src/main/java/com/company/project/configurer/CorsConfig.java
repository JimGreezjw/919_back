package com.company.project.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//跨域配置类
@Configuration
public class CorsConfig {
  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    // 设置setAllowCredentials = true后就不能设置为*了，要设置具体的
    corsConfiguration.addAllowedOrigin("http://192.168.161.6:9528");
    corsConfiguration.addAllowedOrigin("http://localhost:9528");
    corsConfiguration.addAllowedOrigin("http://127.0.0.1:9528");

    corsConfiguration.addAllowedHeader("*"); // 2
    corsConfiguration.addAllowedMethod("*"); // 3
    return corsConfiguration;
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", buildConfig()); // 4
    return new CorsFilter(source);
  }
}
