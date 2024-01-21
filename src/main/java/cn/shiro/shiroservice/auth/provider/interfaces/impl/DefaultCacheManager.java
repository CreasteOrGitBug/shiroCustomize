package cn.shiro.shiroservice.auth.provider.interfaces.impl;

import org.apache.shiro.cache.*;
import org.apache.shiro.util.SoftHashMap;

/**
 * &#064;Time 2024 一月 星期四 16:36
 *
 * @author ShangGuan
 */
public class DefaultCacheManager extends AbstractCacheManager {


    @Override
    protected Cache<Object, Object> createCache(String name) throws CacheException {
        return new MapCache<Object, Object>(name, new SoftHashMap<Object, Object>());
    }
}
