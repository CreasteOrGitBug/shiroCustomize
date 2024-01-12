package cn.shiro.shiroservice.auth.provider.interfaces.impl;

import cn.shiro.shiroservice.auth.utils.PasswordUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;

/**
 * &#064;Time 2024 一月 星期四 18:22
 *
 * @author ShangGuan
 */
public class PasswordServiceReImpl extends DefaultPasswordService {



    public PasswordServiceReImpl() {
        super();
    }
    /**
     * 密码匹配
     *
     * @param submittedPlaintext 提交明文
     * @param saved              保存
     * @return boolean
     */
    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String saved) {
        String submitStr = PasswordUtils.digestHex(submittedPlaintext);
        return saved.equals(submitStr);
    }

    public static void main(String[] args) {
        String admin1 = PasswordUtils.digestHex("admin");
        System.out.println(admin1);
    }
}
