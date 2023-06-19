package com.conciliacion.user.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

import com.conciliacion.user.middleware.TokenValidationFilter;

@Configuration
public class WebConfig {
	public FilterRegistrationBean<TokenValidationFilter> tokenValidationFilterRegistration() {
        FilterRegistrationBean<TokenValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenValidationFilter());
        registrationBean.addUrlPatterns("/*"); 
        return registrationBean;
    }
}
