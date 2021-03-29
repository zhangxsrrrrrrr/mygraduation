package edu.ahau.graduationproject.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created on 2021/2/23.
 *
 * @author Xun Zhang
 */
@SpringBootConfiguration
public class MyConfiguer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**/**/**").addResourceLocations("file:///G:/graduationQuestion/");
    }
}
