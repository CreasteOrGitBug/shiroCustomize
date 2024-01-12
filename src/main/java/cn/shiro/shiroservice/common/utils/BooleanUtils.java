package cn.shiro.shiroservice.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * &#064;Time 2024 一月 星期五 13:54
 *
 * @author ShangGuan
 */
public class BooleanUtils {


    public static boolean[]  toBoolean(List<Boolean> list) {
        boolean[] booleanArray = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            booleanArray[i] = list.get(i);
        }
        return booleanArray;
    }

    public static boolean[] newBooleanSize(int indexSize,Boolean param) {
        boolean[] booleanArray = new boolean[indexSize];
        Arrays.fill(booleanArray, param);
        return booleanArray;
    }
}
