package cn.shiro.shiroservice.common.constant;

import cn.hutool.core.util.IdUtil;

/**
 * &#064;Time 2023 十二月 星期四 15:17
 *
 * @author ShangGuan
 */
public interface JwtUtilsManaPoint {


    /**
     * 类型
     */
    String TYP = "JWT";
    /**
     * 主题
     */
    String SUB = "test";

    String ISS = "系统默认";
    /**
     * 编号
     */
    String JTI = IdUtil.simpleUUID();
}

