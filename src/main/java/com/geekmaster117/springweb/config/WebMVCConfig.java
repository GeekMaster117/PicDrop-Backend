package com.geekmaster117.springweb.config;

import com.geekmaster117.springweb.constants.Urls;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                .allowedOrigins(Urls.frontendUrl)
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
