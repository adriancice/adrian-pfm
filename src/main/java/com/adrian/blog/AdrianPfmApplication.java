package com.adrian.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/**
 * la clase principal del proyecto
 * 
 * @author Adrian Stan
 * @version 1.0 Date 14/09/2018
 */
@SpringBootApplication
public class AdrianPfmApplication implements WebMvcConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(AdrianPfmApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AdrianPfmApplication.class, args);
		logger.info("Aplicacion arrancada");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
}
