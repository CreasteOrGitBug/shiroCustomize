package cn.shiro.shiroservice.auth.provider.absrtact;

import cn.shiro.shiroservice.auth.provider.interfaces.AuthPermissionFilter;
import cn.shiro.shiroservice.auth.provider.interfaces.AuthResolverAware;
import cn.shiro.shiroservice.common.utils.BooleanUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * &#064;Time 2024 一月 星期五 09:24
 *
 * @author ShangGuan
 *
 */
public abstract class AbstractAuthResolverAware implements AuthResolverAware {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthResolverAware.class);

    private PermissionResolver permissionResolver;

    private RolePermissionResolver rolePermissionResolver;


    private final AuthPermissionFilter authPermissionFilter;

    protected AbstractAuthResolverAware(AuthPermissionFilter authPermissionFilter) {
        this(null, null, authPermissionFilter);
    }

    protected AbstractAuthResolverAware(RolePermissionResolver rolePermissionResolver, AuthPermissionFilter authPermissionFilter) {
        this(null, rolePermissionResolver, authPermissionFilter);
    }

    protected AbstractAuthResolverAware(PermissionResolver permissionResolver, AuthPermissionFilter authPermissionFilter) {
        this(permissionResolver, null, authPermissionFilter);

    }

    protected AbstractAuthResolverAware(PermissionResolver permissionResolver
            , RolePermissionResolver rolePermissionResolver,
                                        AuthPermissionFilter authPermissionFilter
    ) {
        if (permissionResolver != null) {
            this.permissionResolver = permissionResolver;
        } else {
            this.permissionResolver = new WildcardPermissionResolver();
        }
        if (rolePermissionResolver != null) {
            this.rolePermissionResolver = rolePermissionResolver;
        }
        this.authPermissionFilter = authPermissionFilter;
    }

    @Override
    public void setPermissionResolver(PermissionResolver resolver) {
        permissionResolver = resolver;
    }

    @Override
    public void setRolePermissionResolver(RolePermissionResolver resolver) {
        rolePermissionResolver = resolver;
    }

    protected RolePermissionResolver getRolePermissionResolver() {
        return this.rolePermissionResolver;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        Permission submitPermission = permissionResolver.resolvePermission(permission);

        return isPermitted(principals, submitPermission);
    }

    @Override
    public boolean isPermitted(PrincipalCollection subjectPrincipal, Permission submitPermission) {

        return assertPermission(subjectPrincipal, submitPermission);
    }

    /**
     * 断言权限
     * 返回true表示权限比较成功
     *
     * @param savePrincipalCollection 保存权限
     * @param submitPermission        提交权限
     * @return boolean
     */

    protected boolean assertPermission(PrincipalCollection savePrincipalCollection, Permission submitPermission) {
        AuthorizationInfo authorizationInfo = authPermissionFilter.doGetAuthorizationInfo(savePrincipalCollection);
        if (authorizationInfo == null) return false;
        //拿到要需要比较的权限
        Collection<Permission> checkPermission = authorizationInfo.getObjectPermissions();
        for (Permission checkPer : checkPermission) {
            //返回true的时候表示具有权限
            boolean implies = submitPermission.implies(checkPer);
            if (implies) {
                return true;
            }
        }
        return false;
    }

    protected boolean assertPermission(PrincipalCollection savePrincipalCollection, Permission... submitPermission) {
        boolean[] booleans = assertPermission(savePrincipalCollection, Arrays.stream(submitPermission).collect(Collectors.toList()));
        for (boolean aBoolean : booleans) {
            if (aBoolean) {
                return true;
            }
        }
        return false;
    }

    protected boolean assertPermission(PrincipalCollection savePrincipalCollection, Collection<Permission> submitPermission) {
        List<Permission> permissions = new ArrayList<>(submitPermission);
        boolean[] booleans = assertPermission(savePrincipalCollection, permissions);
        for (boolean aBoolean : booleans) {
            if (aBoolean) {
                return true;
            }
        }
        return false;
    }

    /**
     * 断言权限
     * 根据每个提交的权限和检查的权限来进程匹配
     *
     * @param savePrincipalCollection 保存本金集合
     * @param submitPermissions       提交权限
     * @return {@link boolean[]}
     */

    protected boolean[] assertPermission(PrincipalCollection savePrincipalCollection, List<Permission> submitPermissions) {
        List<Boolean> result = new ArrayList<>();
        AuthorizationInfo authorizationInfo = authPermissionFilter.doGetAuthorizationInfo(savePrincipalCollection);
        //当AuthorizationInfo==null的时候默认返回false
        if (authorizationInfo == null) return BooleanUtils.newBooleanSize(submitPermissions.size(), false);

        //拿到要需要比较的权限
        List<Permission> checkPermission = new ArrayList<>(authorizationInfo.getObjectPermissions());
        //检查的权限下标
        int checkPermissionIndex = checkPermission.size() - 1;
        //提交的权限下标
        int submitPermissionIndex = submitPermissions.size() - 1;
        for (int i = 0; i <= submitPermissionIndex; i++) { //admin,save
            for (int j = 0; j <= checkPermissionIndex; j++) { //admin ,save,delete
                Permission savePermission = checkPermission.get(i);
                Permission submitPermission = submitPermissions.get(j);
                if (savePermission.implies(submitPermission)) {
                    logger.info("提交的权限:{},需要的权限:{}。匹配成功", submitPermission, savePermission);
                    //当有一次匹配成功那就 这次提交的权限就默认具有权限
                    j = submitPermissions.size();//默认后面的不进行权限判断
                    result.add(true);
                    continue;
                }
                //如果所有提交的权限都匹配完了也没有返回为true的权限 就认为没有权限
                if (j == checkPermissionIndex) result.add(false);
            }
        }
        return BooleanUtils.toBoolean(result);
    }

    @Override
    public boolean[] isPermitted(PrincipalCollection subjectPrincipal, String... submitPermissions) {
        List<Permission> submitPermissionArray = new ArrayList<>();
        for (String permission : submitPermissions) {
            Permission submitPermission = permissionResolver.resolvePermission(permission);
            submitPermissionArray.add(submitPermission);
        }
        return assertPermission(subjectPrincipal, submitPermissionArray);
    }

    @Override
    public boolean[] isPermitted(PrincipalCollection subjectPrincipal, List<Permission> submitPermissions) {
        return assertPermission(subjectPrincipal, submitPermissions);
    }

    @Override
    public boolean isPermittedAll(PrincipalCollection subjectPrincipal, String... permissions) {
        Collection<Permission> resultProcess = new ArrayList<>();
        for (String permission : permissions) {
            Permission submitPermission = permissionResolver.resolvePermission(permission);
            resultProcess.add(submitPermission);
        }
        return assertPermission(subjectPrincipal, resultProcess);
    }

    @Override
    public boolean isPermittedAll(PrincipalCollection subjectPrincipal, Collection<Permission> permissions) {
        return assertPermission(subjectPrincipal, permissions);
    }

    @Override
    public void checkPermission(PrincipalCollection subjectPrincipal, String permission) throws AuthorizationException {
        Permission submitPermission = permissionResolver.resolvePermission(permission);
        checkPermission(subjectPrincipal, submitPermission);
    }

    @Override
    public void checkPermission(PrincipalCollection subjectPrincipal, Permission permission) throws AuthorizationException {
        if (!isPermitted(subjectPrincipal, permission)) {
            logger.warn("User not use auth,{}", permission);
            throw new AuthorizationException("not use permission");
        }
    }

    @Override
    public void checkPermissions(PrincipalCollection subjectPrincipal, String... permissions) throws AuthorizationException {
        Collection<Permission> resultProcess = new ArrayList<>();
        for (String permission : permissions) {
            Permission submitPermission = permissionResolver.resolvePermission(permission);
            resultProcess.add(submitPermission);
        }
        checkPermissions(subjectPrincipal, resultProcess);
    }

    @Override
    public void checkPermissions(PrincipalCollection subjectPrincipal, Collection<Permission> permissions) throws AuthorizationException {
        if (!isPermittedAll(subjectPrincipal, permissions)) {
            logger.warn("User not use auth,{}", permissions.toString());
            throw new AuthorizationException("not use permission");
        }

    }

    @Override
    public boolean hasRole(PrincipalCollection subjectPrincipal, String roleIdentifier) {
        return false;
    }

    @Override
    public boolean[] hasRoles(PrincipalCollection subjectPrincipal, List<String> roleIdentifiers) {
        return new boolean[0];
    }

    @Override
    public boolean hasAllRoles(PrincipalCollection subjectPrincipal, Collection<String> roleIdentifiers) {
        return false;
    }

    @Override
    public void checkRole(PrincipalCollection subjectPrincipal, String roleIdentifier) throws AuthorizationException {

    }

    @Override
    public void checkRoles(PrincipalCollection subjectPrincipal, Collection<String> roleIdentifiers) throws AuthorizationException {

    }

    @Override
    public void checkRoles(PrincipalCollection subjectPrincipal, String... roleIdentifiers) throws AuthorizationException {

    }

}
