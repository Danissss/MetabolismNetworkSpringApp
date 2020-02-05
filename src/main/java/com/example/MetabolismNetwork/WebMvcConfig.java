package com.example.MetabolismNetwork;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// private String current_dir = System.getProperty("user.dir");
    
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/CompoundImages/**").addResourceLocations("file:CompoundImages/");
      // registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
	
//	@Bean
//    public ViewResolver jspViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setSuffix(".html");
//        return bean;
//    }
}