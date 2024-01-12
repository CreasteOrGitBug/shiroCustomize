package cn.shiro.shiroservice.auth.pojo;

import cn.shiro.shiroservice.common.enums.SexEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * &#064;Time 2023 十二月 星期四 15:04
 *
 * @author ShangGuan
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID =1L;

    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 暗语
     */
    private String passWord;

    /**
     * 名称
     */
    private String name;

    /**
     * 盐
     */
    private String salt;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 电话
     */
    private  Long phone;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 是删除 -1删除
     */
    private Integer isDelete;

    /**
     * 会话id
     */
    private String sessionId;


}
