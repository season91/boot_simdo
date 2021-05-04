package com.kh.simdo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //MVC관련. 서블릿관련. 인터셉터같은 애들

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/file/**") // file로 요청하는 모든 접근
                .addResourceLocations("file://C:/CODE/06_Spring/resources/upload/"); //실제 로컬 절대경로

        //WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
