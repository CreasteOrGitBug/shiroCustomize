package cn.shiro.shiroservice.auth.utils;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * &#064;Time 2024 一月 星期四 23:03
 *
 * @author ShangGuan
 */
public class PasswordUtils {


    private  static  final Logger logger= LoggerFactory.getLogger(PasswordUtils.class);

    public static String digestHex(Object submitData){
        // 此处密钥如果有非ASCII字符，考虑编码
        byte[] key = "BASE".getBytes();
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, key);
        String encrypted = mac.digestHex(String.valueOf(submitData));
        logger.info("加密后的字符为:{}",encrypted);
        return encrypted;
    }
}
