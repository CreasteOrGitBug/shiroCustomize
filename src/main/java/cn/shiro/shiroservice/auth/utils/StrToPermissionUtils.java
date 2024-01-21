package cn.shiro.shiroservice.auth.utils;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.util.PermissionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * &#064;Time 2024 一月 星期四 17:56
 *
 * @author ShangGuan
 */
public class StrToPermissionUtils {

    public static Set<Permission> toStrLowerCasePermission(Set<String> permissions) {
        Set<String> processSet = permissions.stream().map(String::toLowerCase).collect(Collectors.toSet());//转化为大写
        return PermissionUtils.resolvePermissions(processSet, new WildcardPermissionResolver());
    }

}
