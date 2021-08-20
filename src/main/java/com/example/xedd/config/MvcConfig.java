package com.example.xedd.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//hoort bij itemcontroller saveItems codejava.net
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        exposeDirectory("item-photos", registry);
//    }
//
//    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
//        Path uploadDir = Paths.get(dirName);
//        String uploadPath = uploadDir.toFile().getAbsolutePath();
//
//        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
//
//        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
//    }
}