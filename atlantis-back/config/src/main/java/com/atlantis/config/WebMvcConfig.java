package com.atlantis.config;

import com.atlantis.common.ProjectPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final String basePath = ProjectPath.getArticlePicturesPath();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/newsArticles/download/**")
                .addResourceLocations("file:" + basePath);
    }
}
