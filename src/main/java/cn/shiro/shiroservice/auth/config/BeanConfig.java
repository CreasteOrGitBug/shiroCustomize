package cn.shiro.shiroservice.auth.config;

import cn.shiro.shiroservice.auth.mapper.ISubjectDaoMapper;
import cn.shiro.shiroservice.auth.mapper.UserMapper;
import cn.shiro.shiroservice.auth.provider.interfaces.AuthenticationInfoFilter;
import cn.shiro.shiroservice.auth.provider.interfaces.impl.DefaultAuthPermission;
import cn.shiro.shiroservice.auth.provider.interfaces.impl.SimpleAuthenticationInfoFilter;
import cn.shiro.shiroservice.auth.provider.interfaces.impl.SimpleRealmImpl;
import cn.shiro.shiroservice.auth.service.impl.ISessionManager;
import cn.shiro.shiroservice.auth.service.impl.ISubjectDaoImpl;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;

import static cn.shiro.shiroservice.common.constant.AuthConstant.BASE_KEY;

/**
 * &#064;Time 2024 一月 星期三 23:39
 *
 * @author ShangGuan
 */
@Configuration
@MapperScan("cn.shiro.**.mapper.**")
public class BeanConfig {

    @Resource
    private  RedisTemplate<Serializable, Object> redisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DefaultAuthPermission defaultAuthPermission;


    @Bean("iRealmImpl")
    public SimpleRealmImpl getIRealmImpl(){
        return new SimpleRealmImpl(defaultAuthPermission, new SimpleAuthenticationInfoFilter(userMapper,null));
    }


    /**
     * 设置session管理
     *
     * @return {@link ISessionManager}
     */

    @Bean("iSessionManager")
    public ISessionManager getISessionManager(){
        return new ISessionManager(redisTemplate);
    }

    @Resource
    private ISubjectDaoMapper iSubjectDaoMapper;

    /**
     * 设置 subject持久化实现
     *
     * @return {@link ISubjectDaoImpl}
     */

    @Bean("iSubjectDaoImpl")
    public ISubjectDaoImpl getISubjectDaoImpl(){
        return new ISubjectDaoImpl(iSubjectDaoMapper);
    }

    /**
     * 记住我 Cookie
     *
     * @return {@link SimpleCookie}
     */

    @Bean("simpleCookie")
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称,对应前端的checkbox的name=rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //设置有效路径
        simpleCookie.setPath("/");
        //设置声明周期 记住我cookie生效时间10秒钟(单位秒)
        simpleCookie.setMaxAge(30*24*60*60);
        return simpleCookie;
    }

    /**
     * cookie管理对象,记住我功能
     *   user表示配置记住我或认证通过可以访问的地址
     * filterChainDefinitionMap.put("/remember", "user");
     * @return {@link CookieRememberMeManager}
     */
    @Bean("cookieRememberMeManager")
    public CookieRememberMeManager rememberMeManager() {

        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥  建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode(BASE_KEY));
        return cookieRememberMeManager;
    }


}
