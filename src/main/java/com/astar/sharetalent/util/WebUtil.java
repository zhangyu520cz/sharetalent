package com.astar.sharetalent.util;

import com.astar.sharetalent.common.constants.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Web工具类
 * @author zhangyu
 * @date 2017/11/16 17:13
 */
@Slf4j
public final class WebUtil {

    /**
     * 保存当前用户
     */
    public static void saveCurrentUser(Object user) {
        setSession(SystemConstants.CURRENT_USER, user);
    }


    /**
     * 保存当前用户
     */
    public static void saveCurrentUser(HttpServletRequest request, Object user) {
        setSession(request, SystemConstants.CURRENT_USER, user);
    }

    /**
     * 获取当前用户
     */
    public static Object getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            try {
                Session session = subject.getSession();
                if (null != session) {
                    return session.getAttribute(SystemConstants.CURRENT_USER);
                }
            } catch (InvalidSessionException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }


    /**
     * 获取当前用户
     */
    public static Object getCurrentUser(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (null != session) {
                return session.getAttribute(SystemConstants.CURRENT_USER);
            }
        } catch (InvalidSessionException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     */
    public static void setSession(Object key, Object value) {
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            Session session = subject.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /**
     * 获取当前登录session
     */
    public static Session getSession(){
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     */
    private static void setSession(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        if (null != session) {
            session.setAttribute(key, value);
        }
    }



    /**
     * 移除当前用户
     */
    public static void removeCurrentUser(HttpServletRequest request) {
        request.getSession().removeAttribute(SystemConstants.CURRENT_USER);
    }


}
