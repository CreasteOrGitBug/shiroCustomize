package cn.shiro.shiroservice.auth.provider.interfaces;

import cn.shiro.shiroservice.auth.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * 身份验证信息过滤器
 * &#064;Time 2024 一月 星期五 15:02
 *
 * @author ShangGuan
 * &#064;date  2024/01/12
 */
public interface AuthenticationInfoFilter {

    /**
     * 身份验证的初始化  在开始的时候调用
     * 可以直接做一些逻辑
     */

    void init();


    /**
     * 获取身份验证信息
     * 可以做一些逻辑处理
     *
     * @param authenticationToken 认证令牌
     * @return {@link AuthenticationInfo}
     */

    AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException;

    /**
     * 判断用户是否存在
     * 可以持久化去查询
     *
     * @param userName 用户名
     * @return {@link User}
     * @throws AuthenticationException 身份验证异常 没有权限的时候抛出的异常
     */

    User userExists(String userName) throws AuthenticationException;


    /**
     * 凭据匹配吗
     *
     * @param token 代币
     * @param info  信息
     * @throws AuthenticationException 身份验证异常
     */

    void doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException;


    /**
     * 身份验证的销毁方法 在结束的时候调用
     */
    void destroyed();
}
