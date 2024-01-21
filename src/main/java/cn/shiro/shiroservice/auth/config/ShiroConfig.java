package cn.shiro.shiroservice.auth.config;

import cn.shiro.shiroservice.auth.provider.interfaces.impl.SimpleRealmImpl;
import cn.shiro.shiroservice.auth.service.impl.ISessionManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * shiro配置
 * &#064;Time 2023 十二月 星期四 13:56
 *
 * @author ShangGuan
 * @date 2023/12/21
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public SecurityManager setSecurityManager(
            @Qualifier("iSessionManager") ISessionManager iSessionManager,
            @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager,
            @Qualifier("iRealmImpl") SimpleRealmImpl iRealm) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager(iRealm);
        //设置记住我管理器
        securityManager.setRememberMeManager(cookieRememberMeManager);
        //设置SubjectDAO的实现 --- Subject持久化
//        securityManager.setSubjectDAO(iSubjectDaoImpl);
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        //设置SessionDAO的实现 --- sessionId的缓存
        sessionManager.setSessionDAO(iSessionManager);
        //设置SessionManager
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 自定义shiro过滤器参数bean，覆盖默认的
     *
     * @return {@link ShiroFilterChainDefinition}
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/**/login", "anon");
        definition.addPathDefinition("/userLogin2", "anon");//放行路径
        //开启shiro内置的退出过滤器，完成退出功能
        definition.addPathDefinition("/logout", "logout");//退出路径
        //definition.addPathDefinition("/main", "anon");
        definition.addPathDefinition("/**", "user"); //表示一下路径会记住我
        return definition;
    }


    /**
     * 设置会话管理器
     *
     * @param cacheSessionDaoConfig 缓存会话dao配置
     * @return {@link DefaultWebSessionManager}
     */

    @Bean("defaultWebSessionManager")
    public DefaultWebSessionManager setSessionManager(@Qualifier("iSessionManager") ISessionManager cacheSessionDaoConfig) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
//        定期验证session
        sessionManager.setSessionValidationSchedulerEnabled(true);
//        删除无效session
        sessionManager.setDeleteInvalidSessions(true);
//         去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
//        设置sessionDAO 将shiro的session交给cacheSessionDaoConfig管理
        sessionManager.setSessionDAO(cacheSessionDaoConfig);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    /**
     * 设置默认web安全管理器
     *
     * @param oAuth2Realm    o auth2领域
     * @param sessionManager 会话管理器
     * @return {@link DefaultWebSecurityManager}
     */

    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager setDefaultWebSecurityManager(@Qualifier("iRealmImpl") SimpleRealmImpl oAuth2Realm,
                                                                  @Qualifier("defaultWebSessionManager") SessionManager sessionManager
    ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 授权属性源顾问 提供注解授权功能
     *
     * @param securityManager 安全管理器
     * @return {@link AuthorizationAttributeSourceAdvisor}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor setAuthorizationAttributeSourceAdvisor(
            @Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager
    ) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
