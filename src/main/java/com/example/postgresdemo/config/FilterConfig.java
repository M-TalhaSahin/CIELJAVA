package com.example.postgresdemo.config;

import com.example.postgresdemo.logfilter.CustomLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomLoggingFilter> loggingFilter() {
        FilterRegistrationBean<CustomLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomLoggingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
