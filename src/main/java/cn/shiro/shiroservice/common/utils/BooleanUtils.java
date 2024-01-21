package cn.shiro.shiroservice.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * &#064;Time 2024 一月 星期五 13:54
 *
 * @author ShangGuan
 */
public class BooleanUtils {


    /**
     * 转化List为Boolean
     *
     * @param list 列表
     * @return {@link boolean[]}
     */

    public static boolean[]  toBoolean(List<Boolean> list) {
        boolean[] booleanArray = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            booleanArray[i] = list.get(i);
        }
        return booleanArray;
    }

    /**
     * 新布尔大小
     *
     * @param indexSize 索引大小
     * @param param     参数
     * @return {@link boolean[]}
     */

    public static boolean[] newBooleanSize(int indexSize,Boolean param) {
        boolean[] booleanArray = new boolean[indexSize];
        Arrays.fill(booleanArray, param);
        return booleanArray;
    }


    /**
     * 具有布尔值true
     *
     * @param isParam 是param
     * @return boolean
     */

    public static boolean hasBooleanTrue(boolean[] isParam){
        for (boolean param : isParam) {
            if(param)return true;

        }
        return false;
    }
}
