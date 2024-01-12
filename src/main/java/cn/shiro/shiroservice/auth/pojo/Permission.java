package cn.shiro.shiroservice.auth.pojo;

import cn.shiro.shiroservice.common.enums.PermissionEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * &#064;Time 2024 一月 星期四 13:52
 *
 * @author ShangGuan
 */
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID =1L;

    /**
     * 身份证件
     */
    private String id;

    /**
     * 名称
     */
    private String name;
    /**
     * 权限
     */
    private PermissionEnums permission;

    /**
     * 状态
     */
    private String state;

    /**
     * 是删除
     */
    private String isDelete;
}
