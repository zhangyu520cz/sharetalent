package com.astar.sharetalent.manage.shiro.config;

import com.astar.sharetalent.manage.shiro.cache.RedisCacheManager;
import com.astar.sharetalent.manage.shiro.cache.RedisSessionDAO;
import com.astar.sharetalent.manage.shiro.realm.SysUserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 *
 * @author zhangyu
 * @date 2018/10/12 13:08
 */
@Configuration
public class ShiroConfiguration {

    @Value("${shiro.session.timeout}")
    private int sessionTimeOut;


    /**
     * shiro过滤器
     * @param securityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", new ShiroAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        /******** start api小程序接口不鉴权 ********/
        filterChainDefinitionMap.put("/api/**", "anon");
        /******** end api小程序接口不鉴权 ********/
        /******** start 测试本包manage接口不鉴权 ********/
        filterChainDefinitionMap.put("/manage/**", "anon");
        /******** start swagger2不鉴权 ********/
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        /******** end swagger2不鉴权 ********/
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SysUserRealm sysUserRealm(){
        SysUserRealm sysUserRealm = new SysUserRealm();
        //sysUserRealm.setAuthenticationCachingEnabled(true);
        //sysUserRealm.setAuthorizationCachingEnabled(true);
        return sysUserRealm;
    }



    /**
     * session管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setGlobalSessionTimeout(sessionTimeOut * 50 * 1000);
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }


    /**
     * 配置核心安全事务管理器
     * @param sysUserRealm
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager(SysUserRealm sysUserRealm, RedisCacheManager redisCacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(sysUserRealm);
        defaultWebSecurityManager.setCacheManager(redisCacheManager);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }


    /**
     * Shiro生命周期处理器
     * @return LifecycleBeanPostProcessor
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
                = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }
}