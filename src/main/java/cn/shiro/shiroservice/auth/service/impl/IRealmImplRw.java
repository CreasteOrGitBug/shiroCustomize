package cn.shiro.shiroservice.auth.service.impl;

import cn.shiro.shiroservice.auth.mapper.UserMapper;
import cn.shiro.shiroservice.auth.pojo.User;
import cn.shiro.shiroservice.auth.provider.interfaces.impl.IAuthenticationTokenImpl;
import cn.shiro.shiroservice.auth.utils.FactoryUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * &#064;Time 2023 十二月 星期四 14:14
 *
 * @author ShangGuan
 */

public class IRealmImplRw extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(IRealmImplRw.class);

    private final UserMapper userMapper;

    /**
     * 凭据匹配器
     */
    private CredentialsMatcher credentialsMatcher;

    public IRealmImplRw(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.credentialsMatcher = FactoryUtils.newPasswordMatcher();
    }

    @Override
    public String getName() {
        return getClass().getName() + "_" + "";
    }

    /**
     * 判断传入的AuthenticationToken是否是支持的 权限Token类
     *
     * @param token 代币
     * @return boolean
     */

    @Override
    public boolean supports(AuthenticationToken token) {
        //判断实现了AuthenticationToken
        if (token instanceof HostAuthenticationToken && token instanceof RememberMeAuthenticationToken) return true;
        throw new UnsupportedTokenException("Not Know AuthenticationToken");
    }

    /**
     * 获取身份验证信息
     *
     * @param authenticationToken 认证令牌
     * @return {@link AuthenticationInfo}
     * @throws AuthenticationException 身份验证异常
     * @throws ClassCastException      类强制转换异常
     */

//    @Override
//    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException, ClassCastException {
//        authenticationTokenCheck(authenticationToken);
//        //根据JDK动态代理转化
//        IAuthenticationTokenImpl iAuthenticationToken = (IAuthenticationTokenImpl) authenticationToken;
//        String userName = iAuthenticationToken.getPrincipal().toString();
//        logger.info("登陆的账号:{}", userName);
//        //判断是否存在当前用户
//        User user = userExists(userName);
//        AuthenticationInfo authenticationInfo;
//        if (credentialsMatcher instanceof PasswordMatcher) {
//            authenticationInfo = newSimpleAuthenticationInfo(userName, user.getPassWord(), iAuthenticationToken.getToken());
//        } else {
//            authenticationInfo = new SimpleAuthenticationInfo(userName, user.getPassWord(), iAuthenticationToken.getToken());
//        }
//        doCredentialsMatch(authenticationToken, authenticationInfo);
//        return authenticationInfo;
//    }




    /**
     * 身份验证令牌检查
     *
     * @param authenticationToken 认证令牌
     */

    private void authenticationTokenCheck(AuthenticationToken authenticationToken) {
        if (!(authenticationToken instanceof IAuthenticationTokenImpl)) {
            logger.info("不是理想的类,应该继承 类 IAuthenticationTokenImpl");
            throw new ClassCastException("不是理想的类");
        }

    }

    /**
     * 新简单身份验证信息
     *
     * @param username 用户名
     * @param password 暗语
     * @param token    token
     * @return {@link SaltedAuthenticationInfo}
     */

    protected SaltedAuthenticationInfo newSimpleAuthenticationInfo(Object username, Object password, String token) {

        return new SimpleAuthenticationInfo(username, password, token);
    }

    /**
     * 密码比较
     *
     * @param token tokenAuthenticationToken– 在身份验证尝试期间提交
     * @param info  存储在系统中.AuthenticationInfo
     */
    protected void doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        //true 如果提供的令牌凭据与存储的帐户凭据匹配， false 否则
        if (!credentialsMatcher.doCredentialsMatch(token, info)) {
            throw new AuthenticationException("身份验证失败");
        }
        logger.info("密码验证通过");
    }


    /**
     * 设置凭据匹配器  默认HashedCredentialsMatcher
     * 如果需要别的比较方式 需要继承重写 TODO 后续这里会重构 避免代码重复
     *
     * @param credentialsMatcher 凭据匹配器
     */
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        this.credentialsMatcher = credentialsMatcher;
    }


    /**
     * 用户是否存在存在
     *
     * @param userName 用户名
     * @throws AuthenticationException 身份验证异常
     */

    private User userExists(String userName) throws AuthenticationException {
        //查询数据库
        User user = userMapper.queryByUserName(userName);
        //判断
        if (user == null) {
            throw new AuthenticationException("Credentials verifyExceptions");
        }
        return user;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }
}
