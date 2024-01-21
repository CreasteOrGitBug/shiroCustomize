package cn.shiro.shiroservice.auth.provider.interfaces.impl;

import cn.hutool.core.stream.CollectorUtil;
import cn.shiro.shiroservice.auth.mapper.PermissionMapper;
import cn.shiro.shiroservice.auth.utils.StrToPermissionUtils;
import cn.shiro.shiroservice.common.utils.ListUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * &#064;Time 2024 一月 星期四 17:14
 *
 * @author ShangGuan
 * 解析角色
 */
@Service
public class DefaultRolePermissionResolver implements RolePermissionResolver {

    @Resource
    private PermissionMapper permissionMapper;
    /**
     * 解析角色中权限 从数据库中解析角色权限
     *
     * @param roleString 角色字符串
     * @return {@link Collection}<{@link Permission}>
     */

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        //获取权限的名称
        List<String> permissionStrArray = permissionMapper.getPermissionStrByRole(roleString);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(new HashSet<>(ListUtils.toList(roleString)));//设置角色

        //设置权限
        Set<String> permissionSet = new HashSet<>(permissionStrArray);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        Set<Permission> permissionSetPermission = StrToPermissionUtils.toStrLowerCasePermission(permissionSet);
        simpleAuthorizationInfo.setObjectPermissions(permissionSetPermission);
        return simpleAuthorizationInfo.getObjectPermissions();
    }


    /**
     * 解析角色中权限
     *
     * @param roleString 角色字符串
     * @return {@link List}<{@link String}>
     */

    public List<String> resolvePermissionsInRole(List<String> roleString){

        return permissionMapper.getPermissionStrByRoles(roleString);
    }
}
