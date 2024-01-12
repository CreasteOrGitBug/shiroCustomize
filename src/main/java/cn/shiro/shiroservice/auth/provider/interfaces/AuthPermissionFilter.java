package cn.shiro.shiroservice.auth.provider.interfaces;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 身份验证权限筛选器
 * 可以在该接口中实现一些需要去持久化中查询操作
 * &#064;Time 2024 一月 星期五 10:10
 *
 * @author ShangGuan
 * @date 2024/01/12
 */
public interface AuthPermissionFilter {

    /**
     * 获取授权信息
     * 根据subject中拿到用户名  随便你怎么去拿权限
     * 可以从存储中获取用户的权限信息
     *
     * @param submitPrincipals 提交的权限信息
     * @return {@link AuthorizationInfo}
     */

    AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection submitPrincipals);

}
