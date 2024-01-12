package cn.shiro.shiroservice.auth.provider.interfaces.impl;

import cn.hutool.core.util.IdUtil;
import cn.shiro.shiroservice.auth.provider.interfaces.AuthPermissionFilter;
import cn.shiro.shiroservice.auth.provider.interfaces.AuthenticationInfoFilter;
import cn.shiro.shiroservice.auth.provider.interfaces.RealmSimple;
import cn.shiro.shiroservice.auth.provider.absrtact.AbstractAuthResolverAware;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * &#064;Time 2023 十二月 星期四 14:14
 *
 * @author ShangGuan
 */

public class SimpleRealmImpl extends AbstractAuthResolverAware implements RealmSimple {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRealmImpl.class);

    /**
     * 身份验证信息过滤器
     */
    private final AuthenticationInfoFilter authenticationInfoFilter;



    public SimpleRealmImpl(AuthPermissionFilter authPermissionFilter, AuthenticationInfoFilter authenticationInfoFilter) {
        super(authPermissionFilter);
        this.authenticationInfoFilter = authenticationInfoFilter;
    }

    @Override
    public String getName() {
        return getClass().getName() + "_" + IdUtil.simpleUUID();
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

    @Override
    public final AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException, ClassCastException {
        //调用初始化方法
        authenticationInfoFilter.init();
        authenticationTokenCheck(authenticationToken);
        AuthenticationInfo authenticationInfo = authenticationInfoFilter.doGetAuthenticationInfo(authenticationToken);
        authenticationInfoFilter.doCredentialsMatch(authenticationToken, authenticationInfo);
        //调用销毁方法
        authenticationInfoFilter.destroyed();
        return authenticationInfo;
    }


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


}
