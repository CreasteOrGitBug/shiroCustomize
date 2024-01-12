package cn.shiro.shiroservice.auth.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.shiro.shiroservice.auth.pojo.User;
import cn.shiro.shiroservice.auth.pojo.vo.out.UserInfoOutVo;
import cn.shiro.shiroservice.common.constant.JwtUtilsManaPoint;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.shiro.shiroservice.common.constant.ManaPoint.ACCOUNT_TOKEN_EXPIRATION_TIME;
import static cn.shiro.shiroservice.common.constant.ManaPoint.TOKEN_ACCOUNT;

/**
 * &#064;Time 2023 十二月 星期四 14:58
 *
 * @author ShangGuan
 */
@Data
public class JwtUtils {


    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    /**
     * 开启jwt校验
     */
    private boolean enable;
    /**
     * jwt签名验证
     */
    private String sign;

    private static final String KEY;
    private static final PublicKey PUBLIC;
    private static final PrivateKey PRIVATE;

    {
        if(StrUtil.isBlank(sign))sign="basic";
    }
    static {
        // 在运行时加入 Bouncy Castle 提供的算法支持
        Security.addProvider(new BouncyCastleProvider());
        // Base64 编码的 PKCS#8 格式的 RSA 私钥
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // 设置密钥长度为2048位
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 获取私钥
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            PUBLIC = publicKey;
            PRIVATE = privateKey;
            KEY = IdUtil.simpleUUID();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }


    public static UserInfoOutVo createToken(User user) {
        UserInfoOutVo userInfoOutVo = new UserInfoOutVo();
        Map<String, Object> map = new HashMap<>();
        map.put(TOKEN_ACCOUNT, user.getUserName());
        String accountToken = generate(map, Long.valueOf(ACCOUNT_TOKEN_EXPIRATION_TIME));
        BeanUtils.copyProperties(user, userInfoOutVo);
        userInfoOutVo.setAccountToken(accountToken);
        userInfoOutVo.setExpireAccountTime(DateUtil.date(System.currentTimeMillis() + ACCOUNT_TOKEN_EXPIRATION_TIME));
        return userInfoOutVo;
    }

    /**
     * 生成
     * 生成token
     *
     * @param claims 要传送消息map,解析后可以通过get获取
     * @param time   时间
     * @return {@link String}
     */
    @SneakyThrows
    public static String generate(Map<String, Object> claims, Long time) {
        Date nowDate = new Date();
        Date noBefore = new Date(System.currentTimeMillis() + 100L);
        //过期时间,设定为一分钟
        Date expireDate = new Date(System.currentTimeMillis() + time);
        //头部信息,可有可无
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", JwtUtilsManaPoint.TYP);
        claims.put("sub", JwtUtilsManaPoint.SUB);
        claims.put("iss", JwtUtilsManaPoint.ISS);
        claims.put("jti", JwtUtilsManaPoint.JTI);
        String jws = Jwts.builder().setHeader(header)
                .setSubject("摄像头工具")
                .setIssuer("系统默认")
                .setClaims(claims)//自定义claims
                .setIssuedAt(nowDate)//当前时间
                .setNotBefore(noBefore)//失效时间
                .setExpiration(expireDate) //过期时间
                .signWith(SignatureAlgorithm.RS384, PRIVATE) // 使用 RS384 签名算法和私钥进行签名
                .compact();
        return KEY + jws;
    }

    /**
     * 生成
     * 生成token
     *
     * @param header 传入头部信息map
     * @param claims 要传送消息map
     * @param time   时间
     * @return {@link String}
     */
    public static String generate(Map<String, Object> header, Map<String, Object> claims, Long time) {
        Date nowDate = new Date();
        Date noBefore = new Date(System.currentTimeMillis() + 100L);
        //过期时间,设定为一分钟
        Date expireDate = new Date(System.currentTimeMillis() + time);
        header.put("typ", JwtUtilsManaPoint.TYP);
        claims.put("sub", JwtUtilsManaPoint.SUB);
        claims.put("iss", JwtUtilsManaPoint.ISS);
        claims.put("jti", JwtUtilsManaPoint.JTI);
        String jws = Jwts.builder().setHeader(header)
                .setClaims(claims)  //自定义claims
                .setIssuedAt(nowDate)//当前时间
                .setNotBefore(noBefore)//失效时间
                .setExpiration(expireDate) //过期时间
                .signWith(SignatureAlgorithm.RS384, PRIVATE) // 使用 RS384 签名算法和私钥进行签名
                .compact();
        return KEY + jws;
    }

    private static String base64Convers(String encrypt2) {
        return encrypt2.replaceAll(KEY, "");

    }

    /**
     * 已签名
     * 校验是不是jwt签名
     *
     * @param token 代币
     * @return boolean
     */
    public static boolean isSigned(String token) {
        return Jwts.parser()
                .setSigningKey(PUBLIC)
                .isSigned(base64Convers(token));
    }

    /**
     * 验证
     * 校验签名是否正确
     *
     * @param token 代币
     * @return boolean
     */
    public static boolean verify(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(PUBLIC)
                    .parseClaimsJws(base64Convers(token));
            return isSigned(token);
        } catch (Exception e) {
            throw new AuthenticationException("token失效");
        }
    }

    /**
     * 获取payload 部分内容（即要传的信息）
     * 使用方法：如获取userId：getClaim(token).get("userId");
     *
     * @param token 代币
     * @return {@link Claims}
     */
    public static Claims getClaim(String token) {
        if (!verify((token))) throw new AuthenticationException("没有权限,token无效");
        try {
            return Jwts.parser()
                    .setSigningKey(PUBLIC)
                    .parseClaimsJws(base64Convers(token))
                    .getBody();
        } catch (Exception e) {
            throw new JwtException("没有权限,token解析错误");
        }
    }

    /**
     * 获取头部信息map
     * 使用方法 : getHeader(token).get("alg");
     *
     * @param token 代币
     * @return {@link JwsHeader}
     */
    public static JwsHeader getHeader(String token) {
        JwsHeader header = null;
        try {
            header = Jwts.parser()
                    .setSigningKey(PUBLIC)
                    .parseClaimsJws(base64Convers(token))
                    .getHeader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return header;
    }

    /**
     * 获取jwt发布时间
     *
     * @param token 代币
     * @return {@link Date}
     */
    public static Date getIssuedAt(String token) {
        return getClaim(base64Convers(token)).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     *
     * @param token 代币
     * @return {@link Date}
     */
    public static Date getExpiration(String token) {
        return getClaim(base64Convers(token)).getExpiration();
    }

    /**
     * 验证token是否失效
     *
     * @param token 代币
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiration(base64Convers(token));
            return expiration.before(new Date());
        } catch (Exception exception) {
            throw new JwtException("token失效");
        }
    }

    /**
     * 直接Base64解密获取header内容
     *
     * @param token 代币
     * @return {@link String}
     */
    public static String getHeaderByBase64(String token) {
        String header = null;
        if (isSigned(base64Convers(token))) {
            try {
                byte[] header_byte = Base64.getDecoder().decode(base64Convers(token).split("\\.")[0]);
                header = new String(header_byte);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return header;
    }

    /**
     * 直接Base64解密获取payload内容
     *
     * @param token 代币
     * @return {@link String}
     */
    public static String getPayloadByBase64(String token) {
        String payload = null;
        if (isSigned(base64Convers(token))) {
            try {
                byte[] payload_byte = Base64.getDecoder().decode(base64Convers(token).split("\\.")[1]);
                payload = new String(payload_byte);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return payload;
    }

    /**
     * 验证标志
     * 线上验证 线下只是解码不验证
     *
     * @param jjwtToken jjwt代币
     * @return {@link Claims}
     */

    public  Claims verifySign(String jjwtToken) {
        try {
            if (enable) {
                Claims claims = Jwts.parser().setSigningKey(sign).parseClaimsJws(base64Convers(jjwtToken)).getBody();
                if (Jwts.parser().setSigningKey(sign).isSigned(base64Convers(jjwtToken))) return claims;
                throw new IllegalArgumentException("jwt无效");
            } else {
                String nodeStr = jjwtToken.substring(jjwtToken.lastIndexOf(".") + 1);
                String jws = jjwtToken.replaceAll(nodeStr, "");
                return Jwts.parser()
                        .parseClaimsJwt(base64Convers(jws)).getBody();
            }
        } catch (Exception e) {
            logger.error("jwt验证错误{}", e.getMessage());
            throw new IllegalArgumentException("jwt无效");
        }
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put(TOKEN_ACCOUNT, "admin");
        String generate = generate(map, System.currentTimeMillis()+123131241231L);
        System.out.println(generate);

    }


}
