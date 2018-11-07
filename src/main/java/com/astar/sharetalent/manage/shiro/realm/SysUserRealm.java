package com.astar.sharetalent.manage.shiro.realm;

import com.astar.sharetalent.util.WebUtil;
import com.astar.sharetalent.web.service.SysOperService;
import com.astar.sharetalent.web.vo.SysOperVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * shiro权限获取
 * @author zhangyu
 * @date 2017/11/29 15:00
 */
@Slf4j
public class SysUserRealm extends AuthorizingRealm {

    @Autowired
    private SysOperService sysOperService;
    /**
     * 权限验证
     * @param principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permissions = (Set<String>) WebUtil.getSession().getAttribute("permission");
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }


    /**
     * 登录验证
     * @param token
     * @return AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        SysOperVo sysUserVo = sysOperService.selectByUserName(usernamePasswordToken.getUsername());
        if (null == sysUserVo) {
            throw new DisabledAccountException();
        }
        if (!sysUserVo.getPassword().equals(new String(usernamePasswordToken.getPassword()))) {
            throw new IncorrectCredentialsException();
        }
        WebUtil.saveCurrentUser(sysUserVo);
        return new SimpleAuthenticationInfo(sysUserVo.getUserName(), sysUserVo.getPassword(), sysUserVo.getUserName());

    }


}