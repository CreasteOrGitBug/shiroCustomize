package cn.shiro.shiroservice.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;


/**
 * &#064;Time 2024 一月 星期四 15:07
 *
 * @author ShangGuan
 */
public class Md5HashUtils {


    /**
     * 转换简单散列
     *
     * @param source 来源
     * @return {@link SimpleHash}
     * @throws ClassCastException 类强制转换异常
     */

    public static SimpleHash conversSimpleHash(Object source) throws ClassCastException{
        if(source instanceof SimpleHash){
            return (SimpleHash)source;
        }
        throw new ClassCastException("不能直接转化对象");
    }


    /**
     * 获取哈希值 Str
     *
     * @param source 来源
     * @return {@link String}
     * @throws ClassCastException 类强制转换异常
     */

    public static String getHashStrValue(Object source) throws ClassCastException{
        SimpleHash simpleHash = conversSimpleHash(source);
        return ByteUtils.conversCharacter(simpleHash.getBytes());
    }

}
