package cn.shiro.shiroservice.auth.service.impl;

import cn.shiro.shiroservice.auth.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * &#064;Time 2023 十二月 星期四 14:14
 *
 * @author ShangGuan
 */

public class IRealmImpls extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(IRealmImpls.class);

    private final UserMapper userMapper;

    /**
     * 凭据匹配器
     */
    private CredentialsMatcher credentialsMatcher;

    public IRealmImpls(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.credentialsMatcher=new PasswordMatcher();
    }


//    从基础数据存储中检索给定主体的 AuthorizationInfo。从此方法返回实例时，可能需要考虑使用 的 SimpleAuthorizationInfo实例，因为它在大多数情况下都是合适的。
//    形参:
//    principals – 应检索的 AuthorizationInfo 的主要标识主体。
//    返回值:
//    与此主体关联的 AuthorizationInfo。
//    请参阅:
//    SimpleAuthorizationInfo

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

//    从特定于实现的数据源（RDBMS、LDAP 等）中检索给定身份验证令牌的身份验证数据。
//    对于大多数数据源来说，这意味着只需“提取”关联主体/用户的身份验证数据，仅此而已，剩下的工作就交给 Shiro 完成。但是在一些系统中，除了检索数据之外，这种方法实际上还可以执行特定于 EIS 的登录逻辑 - 这取决于 Realm 的实现。
//    null返回值表示任何帐户都不能与指定的令牌关联。
//    形参:
//    token – 包含用户主体和凭据的身份验证令牌。
//    返回值:
//    仅 AuthenticationInfo 当查找成功时（即帐户存在且有效等），才包含身份验证生成的帐户数据的对象
//    抛出:
//    AuthenticationException– 如果为指定令牌获取数据或执行特定于领域的身份验证逻辑时出错

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }
}
