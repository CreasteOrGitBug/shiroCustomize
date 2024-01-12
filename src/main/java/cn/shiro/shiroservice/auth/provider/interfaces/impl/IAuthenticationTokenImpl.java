package cn.shiro.shiroservice.auth.provider.interfaces.impl;

import cn.shiro.shiroservice.auth.utils.JwtUtils;
import cn.shiro.shiroservice.common.constant.ManaPoint;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * &#064;Time 2023 十二月 星期四 14:19
 *
 * @author ShangGuan
 */
public class IAuthenticationTokenImpl implements HostAuthenticationToken, RememberMeAuthenticationToken {


    @Getter
    private final String username;

    @Getter
    private  String token;

    private final String password;
    @Getter
    private  Claims claim;

    private final String host;
    private final boolean rememberMe;


    public IAuthenticationTokenImpl(String username, String password) {
        this(username, null, password, null);
    }

    public IAuthenticationTokenImpl(String username, String token, String password) {
        this(username, token, password, null);
    }

    public IAuthenticationTokenImpl(String username, String token, String password, String host) {
        this(username, token, password, host, false);
    }

    public IAuthenticationTokenImpl(String username, String token, String password, String host, boolean rememberMe) {
        this(username, token, password, (token != null ? new JwtUtils().verifySign(token) : null), host, rememberMe);
    }

    public IAuthenticationTokenImpl(String username, String token, String password, Claims claim, String host, boolean rememberMe) {
        this.username = username;
        if(token!=null){
            this.token = token;
            this.claim = claim;
        }
        this.password = password;
        this.host = host;
        this.rememberMe = rememberMe;
    }

    /**
     * 获取 userName
     *
     * @return {@link Object}
     */

    @Override
    public Object getPrincipal() {
        return username;
    }

    /**
     * 获取 passWord
     *
     * @return {@link Object}
     */

    @Override
    public Object getCredentials() {
        return password;
    }

    /**
     * 获取主机
     *
     * @return {@link String}
     */

    @Override
    public String getHost() {
        return host;
    }

    /**
     * 是记住我
     *
     * @return boolean
     */

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }
}
