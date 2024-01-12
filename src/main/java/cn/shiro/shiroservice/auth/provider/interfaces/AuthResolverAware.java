package cn.shiro.shiroservice.auth.provider.interfaces;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.PermissionResolverAware;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolverAware;

/**
 * &#064;Time 2024 一月 星期五 09:22
 *
 * @author ShangGuan
 */
public interface AuthResolverAware extends Authorizer, PermissionResolverAware, RolePermissionResolverAware {

    /**
     * 设置权限解析程序
     *
     * @param pr 公共关系
     */

    @Override
    void setPermissionResolver(PermissionResolver pr);

    /**
     * 设置角色权限解析程序
     *
     * @param rpr rpr
     */

    @Override
    void setRolePermissionResolver(RolePermissionResolver rpr);

}
