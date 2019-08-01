package com.tensquare.user.config;

import com.tensquare.user.inteceptor.JwtInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class InteceptorConfg extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInteceptor jwtInteceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器要声明拦截器对象和拦截器的请求
        registry.addInterceptor(jwtInteceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");
    }
}
