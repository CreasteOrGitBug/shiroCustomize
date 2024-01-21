package cn.shiro.shiroservice.auth.provider.absrtact;

import cn.shiro.shiroservice.auth.provider.interfaces.AuthPermissionFilter;
import cn.shiro.shiroservice.auth.provider.interfaces.AuthResolverAware;
import org.apache.shiro.realm.CachingRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * &#064;Time 2024 一月 星期五 09:24
 *
 * @author ShangGuan
 */
public abstract class AbstractCacheAware extends CachingRealm implements AuthResolverAware {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCacheAware.class);


    private final AuthPermissionFilter authPermissionFilter;



    protected AbstractCacheAware(AuthPermissionFilter authPermissionFilter) {
        this.authPermissionFilter = authPermissionFilter;
    }


    protected AuthPermissionFilter getAuthPermissionFilter() {
        return authPermissionFilter;
    }






}
