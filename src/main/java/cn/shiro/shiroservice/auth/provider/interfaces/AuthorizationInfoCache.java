package cn.shiro.shiroservice.auth.provider.interfaces;

import org.apache.shiro.cache.Cache;

/**
 * &#064;Time 2024 一月 星期四 16:34
 *
 * @author ShangGuan
 * AuthorizationInfo缓存接口
 */
public interface AuthorizationInfoCache<KEY,VALUE> {


    /**
     * 获取可用授权缓存
     *
     *
     * @return {@link Cache}<{@link KEY}, {@link VALUE}>
     */

    Cache<KEY, VALUE> getAvailableAuthorizationCache();




}
