package com.sam.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.sam.webapp.interceptor.Interceptador;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Interceptador()).addPathPatterns("/**");
	}
}
