package com.eliefly.bos.service.system;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eliefly.bos.dao.system.UserRepository;
import com.eliefly.bos.domain.system.User;

/**
 * ClassName:UserRealm <br/>
 * Function: <br/>
 * Date: 2017年12月12日 下午9:37:38 <br/>
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    /*
     * 授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection arg0) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 授予权限
        // 方式1: Spring 配置文件 shiro URL过滤器拦截实现权限控制. 
        info.addStringPermission("areaAction_pageQuery");

        // 方式2.注解权限设置
        info.addStringPermission("courier:delete");

        // 方式3.shiro标签设置
        info.addStringPermission("courier_delete");

        return info;
    }

    /*
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {

        // 强制类型转换
        UsernamePasswordToken token =
                (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();

        User user = userRepository.findByUsername(username);

        // 用户不存在, 直接返回null
        if (user == null) {

            return null;
        }

        /*
         * @param principal(身份) : the 'primary' principal associated with the specified realm.
         * 调用subject.getPrincipal()时返回的对象 <br/> 通常登录成功后,我们会将获取的User存入Session,所以这里使用user
         * 
         * @param credentials(凭证) : the credentials that verify the given principal. <br/>
         * 和第一个参数相关的密码
         * 
         * @param realmName : the realm from where the principal and credentials were acquired.
         * 从那个realm获取前两个参数,通常都是getName()
         */
        // 找到用户,创建认证信息,至于密码是否正确,交由框架去处理
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
                user.getPassword(), getName());

        return info;
    }

}
