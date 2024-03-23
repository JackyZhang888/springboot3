package com.example.demo.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义资源配置类，用于配置静态资源路径。
 * @Configuration 表示这是一个配置类。
 * @WebMvcConfigurer 使该类能够为Spring MVC提供额外的配置。
 */
@Configuration
public class CustomResourceConfig implements WebMvcConfigurer {
    // 通过注入的方式获取上传文件的路径
    @Value("${file.upload.path}")
    private String path;

    /**
     * 配置静态资源路径。
     * @param registry 资源处理器注册表，用于添加资源处理器。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将所有请求映射到指定的文件路径上
        registry.addResourceHandler("/**/**")
                .addResourceLocations("file:" + path);

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
