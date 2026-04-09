package com.polymer.framework.common.utils;

import java.util.Arrays;

public class ArrayUtil {

    /**
     * 替换 ArrayUtil.contains - 使用 Arrays.binarySearch（需要先排序）
     */
    public static boolean contains(Object[] array, Object value) {
        if (array == null || array.length == 0) {
            return false;
        }
        return Arrays.asList(array).contains(value);
    }

    /**
     * 针对字符串数组的优化版本
     */
    public static boolean contains(String[] array, String value) {
        if (array == null || array.length == 0 || value == null) {
            return false;
        }

        for (String element : array) {
            if (value.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
