package com.zis.bookinfo.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// @Configuration
// 与spring文档略有不同，问题暂未解决
// http://docs.spring.io/spring-data/jpa/docs/1.6.1.RELEASE/reference/html/repositories.html#web-pagination
public class WebConfig extends WebMvcConfigurationSupport {
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	    PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
	    resolver.setFallbackPageable(new PageRequest(0, 5));
	    argumentResolvers.add(resolver);
	}
}
