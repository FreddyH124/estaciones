package com.arso.pasarela.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }
}
