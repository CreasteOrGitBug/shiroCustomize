package cn.shiro.shiroservice.common.utils;

import java.util.Base64;

/**
 * &#064;Time 2024 一月 星期四 15:04
 *
 * @author ShangGuan
 */
public class ByteUtils {


    /**
     * 转化为字符串
     *
     * @param bytes 字节
     * @return {@link String}
     */

    public static  String conversCharacter(byte[] bytes){
       return Base64.getEncoder().encodeToString(bytes);//不指定UTF-8会乱码
    }

}
