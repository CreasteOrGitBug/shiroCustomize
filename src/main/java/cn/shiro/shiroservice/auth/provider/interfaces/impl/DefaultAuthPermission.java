package cn.shiro.shiroservice.auth.provider.interfaces.impl;

import cn.shiro.shiroservice.auth.mapper.PermissionMapper;
import cn.shiro.shiroservice.auth.pojo.User;
import cn.shiro.shiroservice.auth.pojo.dto.PermissionDTO;
import cn.shiro.shiroservice.auth.provider.interfaces.AuthPermissionFilter;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.PermissionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * &#064;Time 2024 一月 星期五 10:39
 *
 * @author ShangGuan
 */
@Component
public class DefaultAuthPermission implements AuthPermissionFilter {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthPermission.class);


    /**
     * 默认最高系统权限  类似于**:**:**
     */
    private String permission = "super_admin";

    private final String generalPermission = "*:*:*";

    /**
     * 禁止开启通配符 默认不会开启  TODO 现在还没有完善
     */
    private boolean disableGeneral;


    public String getPermission() {
        return permission;
    }


    /**
     * 设置默认放行权限 TODO 设置实际的权限 类似于*:*:*
     *
     * @param permission 准许
     */

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setDisableGeneral(boolean disableGeneral) {
        this.disableGeneral = disableGeneral;
    }

    private final PermissionMapper permissionMapper;

    @Autowired
    public DefaultAuthPermission(@Qualifier("permissionMapper") PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
        this.disableGeneral = true;
    }


    /**
     * 从数据库中去查询
     *
     * @param submitPrincipals 提交主体
     * @return {@link AuthorizationInfo}
     */

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection submitPrincipals) {
        User user = (User) submitPrincipals.getPrimaryPrincipal();
        //查询数据库
        List<PermissionDTO> permissionDTO = permissionMapper.getPermissionDTO(user.getUserName());
        Set<String> roleNameSet = permissionDTO.stream().map(PermissionDTO::getRoleName).collect(Collectors.toSet());
        Set<String> permissionSet = permissionDTO.stream().map(PermissionDTO::getPermission).collect(Collectors.toSet());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roleNameSet);
        //获取权限
        simpleAuthorizationInfo.setStringPermissions(checkStrSuperAuth(permissionSet));
        simpleAuthorizationInfo.setObjectPermissions(checkSuperAuth(permissionSet));
        logger.info("用户权限为,{}", permissionSet);
        return simpleAuthorizationInfo;
    }


    /**
     * 检查超级身份验证 如果用户具有最高级别的身份权限
     * 那么就赋值给用户 TODO *:*:*
     *
     * @return {@link Set}<{@link String}>
     */

    protected Set<Permission> checkSuperAuth(Set<String> permissions) {
        Set<String> processSet = permissions.stream().map(String::toLowerCase).collect(Collectors.toSet());
        if (processSet.contains(permission.toLowerCase()) && !disableGeneral) {
            Set<String> permissionSet = new HashSet<>();
            permissionSet.add(generalPermission);
            return PermissionUtils.resolvePermissions(permissionSet, new WildcardPermissionResolver());
        }
        return PermissionUtils.resolvePermissions(permissions, new WildcardPermissionResolver());
    }

    protected Set<String> checkStrSuperAuth(Set<String> permissions) {
        Set<String> processSet = permissions.stream().map(String::toLowerCase).collect(Collectors.toSet());
        if (processSet.contains(permission.toLowerCase()) && !disableGeneral) {
            Set<String> permissionSet = new HashSet<>();
            permissionSet.add(generalPermission);
            return permissionSet;
        }
        return permissions;
    }


}
