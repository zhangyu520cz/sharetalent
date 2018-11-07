package com.astar.sharetalent.filter;

import com.astar.sharetalent.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyu
 * @description 参数校验拦截器
 * @date 2018/7/31 上午10:58
 */
@Slf4j
@Component
public class ParamCheckInterceptor extends HandlerInterceptorAdapter {
    //null字符串
    public static final String STRING_NULL = "null";
    //undefined字符串
    public static final String UNDEFINED = "undefined";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,String> paramMap = new HashMap<>(16);
        while (enumeration.hasMoreElements()) {
            String paramKey = enumeration.nextElement();
            String paramValue = request.getParameter(paramKey);
            paramMap.put(paramKey,paramValue);
        }
        log.info("请求URI为{},对应的参数为：{}",request.getRequestURI(),paramMap);
        if (hasStringNull(paramMap)) {
            throw new BusinessException("前端参数里带有null字符串");
        }
        if (hasUndefined(paramMap)) {
            throw new BusinessException("前端参数里带有undefined");
        }
        return true;
    }
    //参数里是否有null字符串
    private boolean hasStringNull(Map<String,String> paramMap) {
        if (MapUtils.isNotEmpty(paramMap)) {
            for (Map.Entry entry:paramMap.entrySet()) {
                if (STRING_NULL.equals(entry.getValue())) {
                    log.info("参数名：{}，前端传的值为：{}",entry.getKey(),entry.getValue());
                    return true;
                }
            }
        }
        return false;
    }

    //参数里是否有undefined字符串
    private boolean hasUndefined(Map<String,String> paramMap) {
        if (MapUtils.isNotEmpty(paramMap)) {
            for (Map.Entry entry:paramMap.entrySet()) {
                if (UNDEFINED.equals(entry.getValue())) {
                    log.info("参数名：{}，前端传的值为：{}",entry.getKey(),entry.getValue());
                    return true;
                }
            }
        }
        return false;
    }
}
