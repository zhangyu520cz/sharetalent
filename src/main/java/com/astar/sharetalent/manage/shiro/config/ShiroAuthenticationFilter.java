package com.astar.sharetalent.manage.shiro.config;

import com.alibaba.fastjson.JSON;
import com.astar.sharetalent.common.R;
import com.astar.sharetalent.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jujun chen
 * @description:
 * @date: 2018/7/3
 */
@Slf4j
public class ShiroAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            response.setContentType("application/json;charset=utf-8");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().print(JSON.toJSON(R.error("1006","登录过期")));
            return false;
        }
    }

}
