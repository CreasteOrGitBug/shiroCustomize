package cn.shiro.shiroservice.common.utils;

import cn.hutool.core.collection.ListUtil;

import java.util.Collections;
import java.util.List;

/**
 * &#064;Time 2024 一月 星期四 17:44
 *
 * @author ShangGuan
 */
public class ListUtils {



    public static  <T> List<T>  toList(T param){
        return Collections.singletonList(param);
    }

    public static void main(String[] args) {
        List<String> sad = toList("sad");
        System.out.println(sad);

    }

}
