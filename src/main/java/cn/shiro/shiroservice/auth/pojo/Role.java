package cn.shiro.shiroservice.auth.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * &#064;Time 2024 一月 星期四 13:34
 *
 * @author ShangGuan
 */
@Data
public class Role implements Serializable {

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
     * 状态
     */
    private String state;

    /**
     * 是删除
     */
    private String isDelete;
}
