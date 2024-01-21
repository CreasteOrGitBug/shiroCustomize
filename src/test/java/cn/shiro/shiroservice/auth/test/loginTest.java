package cn.shiro.shiroservice.auth.test;


import cn.shiro.shiroservice.auth.provider.interfaces.impl.IAuthenticationTokenImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Map;

/**
 * &#064;Time 2024 一月 星期四 16:26
 *
 * @author ShangGuan
 */
@SpringBootTest
public class loginTest {


    @Resource(name = "securityManager")
    private SecurityManager SecurityManager;
    String token="eyJhbGciOiJSUzM4NCIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0IiwibmJmIjoxNzA0MzgwMzYzLCJpc3MiOiLns7vnu5_pu5jorqQiLCJ0b2tlbjphY2NvdW50IjoiYWRtaW4iLCJleHAiOjM1MzE4OTE5NjgsImlhdCI6MTcwNDM4MDM2MywianRpIjoiOTk3OGJmODEwNWM2NGFmZTgyZWNmNTI3MjBjNzIxNDkifQ.YODtj6prYVLEgAs1bZ9sjDhIcSA0FeoSzZSqOwoFAih3UwHZhNWBvkEw4FpAdAhKLmGACoWPl1bHlFkuPOIst5IG84ljFyDtF4Ed8m72LOqrFu8DKJ4Ln6PIkSChEdA4Up2Dm-K5ZPfF3G1mYBVxWesDYm1h9Ee2amLqiyKSfgnekp3vY7t8vaxNY8N8yyJtDEiq0MYnbJlQhtv20u1gIh5ltLZIN_cu1JsTx2-WTw-wl55iw59uyXaFpDeV8Qf4l0FAp1Md92lI2-PjNGlFdln7ZqZHVSJUV9URindwOqV3kdaqC5Qj7NHZedAJEuxTx5tC9JkhPc21WDyJyFxjKg";
    @Test
    public void login(){
        SecurityUtils.setSecurityManager(SecurityManager);

        Subject subject = SecurityUtils.getSubject();
        IAuthenticationTokenImpl iAuthenticationToken = new IAuthenticationTokenImpl(token, "admin");
        subject.login(iAuthenticationToken);

    }

    /**
     * 测试登陆权限验证是否正确
     */

    @Test
    public void authTest(){
        SecurityUtils.setSecurityManager(SecurityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new IAuthenticationTokenImpl("admin", "admin"));
        boolean[] permitted = subject.isPermitted("super_admin", "CHAO");
        boolean asd = subject.isPermittedAll("asd");
        boolean admin = subject.isPermittedAll("super_admin","ads"); //传入俩个没有权限
        boolean admin1 = subject.hasRole("admin");
        boolean admin2 = subject.hasRole("admin2");

        System.out.println(permitted);
    }

    @Test
    public void printlnClassFilter(){
    }


    public static  class Test1{
        private Realm realm;

        private Authorizer authorizer;

        public static void main(String[] args) {
            DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
            defaultSecurityManager.setSessionManager(defaultSecurityManager.getSessionManager());
            Thread thread = new Thread();
            ArrayList<Object> list = new ArrayList<>();


        }
    }


}
