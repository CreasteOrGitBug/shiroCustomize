package cn.shiro.shiroservice.auth.utils;

import cn.shiro.shiroservice.auth.provider.interfaces.impl.PasswordServiceReImpl;
import org.apache.shiro.authc.credential.PasswordMatcher;

/**
 * &#064;Time 2024 一月 星期四 22:50
 *
 * @author ShangGuan
 */
public class FactoryUtils {


    public static PasswordMatcher newPasswordMatcher(){
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        passwordMatcher.setPasswordService(new PasswordServiceReImpl());
        return passwordMatcher;
    }
}
