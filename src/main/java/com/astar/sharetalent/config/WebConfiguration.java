package com.astar.sharetalent.config;

import com.astar.sharetalent.filter.ParamCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: jujun chen
 * @description:  自定义拦截器继承该类
 * @date: 2018/7/2
 */
@Component
public class WebConfiguration implements WebMvcConfigurer,ErrorPageRegistrar {

    @Autowired
    private ParamCheckInterceptor paramCheckInterceptor;

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404"));
        registry.addErrorPages(new ErrorPage(HttpStatus.BAD_GATEWAY,"/502"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paramCheckInterceptor).order(-1).addPathPatterns("/**");
    }

}
