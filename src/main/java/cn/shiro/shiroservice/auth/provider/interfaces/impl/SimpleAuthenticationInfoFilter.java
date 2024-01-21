package cn.shiro.shiroservice.auth.provider.interfaces.impl;

import cn.hutool.core.util.IdUtil;
import cn.shiro.shiroservice.auth.mapper.UserMapper;
import cn.shiro.shiroservice.auth.pojo.User;
import cn.shiro.shiroservice.auth.provider.interfaces.AuthenticationInfoFilter;
import cn.shiro.shiroservice.auth.utils.FactoryUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * &#064;Time 2024 一月 星期五 15:06
 *
 * @author ShangGuan
 */
public class SimpleAuthenticationInfoFilter implements AuthenticationInfoFilter {

    /**
     * 凭据匹配器
     */
    private CredentialsMatcher credentialsMatcher;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthPermission.class);

    public SimpleAuthenticationInfoFilter(UserMapper userMapper, CredentialsMatcher credentialsMatcher) {
        this.userMapper = userMapper;
        if (credentialsMatcher instanceof PasswordMatcher) {
            this.credentialsMatcher = credentialsMatcher ;
        } else if (credentialsMatcher==null) {
            this.credentialsMatcher = FactoryUtils.newPasswordMatcher();
        }
    }

    @Override
    public void init() {
        logger.info("身份验证开始,{}", System.currentTimeMillis());
    }

    public String getName() {
        return getClass().getName() + "_" + IdUtil.simpleUUID();
    }

    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //根据JDK动态代理转化
        IAuthenticationTokenImpl iAuthenticationToken = (IAuthenticationTokenImpl) authenticationToken;
        String userName = iAuthenticationToken.getPrincipal().toString();
        logger.info("登陆的账号:{}", userName);
        //判断是否存在当前用户
        User user = userExists(userName);
        return new SimpleAuthenticationInfo(user, user.getPassWord(), getName());
    }

    @Override
    public User userExists(String userName) throws AuthenticationException {
        //查询数据库
        User user = userMapper.queryByUserName(userName);
        //判断
        if (user == null) throw new AuthenticationException("Credentials verifyExceptions");
        return user;
    }


    @Override
    public void doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        //true 如果提供的令牌凭据与存储的帐户凭据匹配， false 否则
        if (!credentialsMatcher.doCredentialsMatch(token, info)) throw new AuthenticationException("身份验证失败");
        logger.info("密码验证通过");
    }

    @Override
    public void destroyed() {
        logger.info("身份验证结束,{}", System.currentTimeMillis());
    }

    /**
     * 设置凭据匹配器  默认HashedCredentialsMatcher
     * 如果需要别的比较方式 需要继承重写 TODO 后续这里会重构 避免代码重复
     *
     * @param credentialsMatcher 凭据匹配器
     */
    private void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        if (credentialsMatcher instanceof PasswordMatcher) {
            this.credentialsMatcher = credentialsMatcher ;
        } else if (credentialsMatcher==null) {
            this.credentialsMatcher = FactoryUtils.newPasswordMatcher();
        }
    }
}
