package cn.shiro.shiroservice.common.enums;

import lombok.Getter;

/**
 * &#064;Time 2024 一月 星期四 11:07
 *
 * @author ShangGuan
 */
public enum SexEnum {

    /**
     * 未知
     */
    NO_KNOW("未知"),

    /**
     * 男
     */
    MAN("男"),
    /**
     * 女
     */
    WOMAN("女");

    @Getter
    private final String typeName;
    SexEnum(String typeName) {
         this.typeName=typeName;
    }
}
