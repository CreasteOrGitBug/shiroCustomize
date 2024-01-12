package cn.shiro.shiroservice.auth.pojo.dto;

import lombok.Data;

/**
 * &#064;Time 2024 一月 星期五 10:53
 *
 * @author ShangGuan
 */
@Data
public class PermissionDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限
     */
    private String permission;

}
