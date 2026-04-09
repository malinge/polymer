package com.polymer.framework.common.utils;

import com.polymer.framework.common.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 校验工具类
 *
 * @author polymer
 */
public class AssertUtils {

    public static void isBlank(String str, String variable) {
        if (StringUtils.isBlank(str)) {
            throw new ServiceException(variable + "不能为空");
        }
    }

    public static void isNull(Object object, String variable) {
        if (object == null) {
            throw new ServiceException(variable + "不能为空");
        }
    }

    public static void isArrayEmpty(Object[] array, String variable) {
        if (ArrayUtils.isEmpty(array)) {
            throw new ServiceException(variable + "不能为空");
        }
    }

}