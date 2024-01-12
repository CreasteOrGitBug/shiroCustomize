package cn.shiro.shiroservice.common.constant;

/**
 * &#064;Time 2023 十二月 星期四 15:08
 *
 * @author ShangGuan
 */
public interface ManaPoint {

    /**
     * 代币帐户
     */
    String TOKEN_ACCOUNT = "token:account";

    /**
     * 帐户令牌过期时间
     */
    Integer ACCOUNT_TOKEN_EXPIRATION_TIME = 18000 * 1000;

    /**
     * 刷新令牌过期时间
     */
    Integer REFRESH_TOKEN_EXPIRATION_TIME=36000 * 1000;

}
