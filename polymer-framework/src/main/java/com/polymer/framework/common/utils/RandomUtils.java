package com.polymer.framework.common.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    /**
     * 用于随机选的数字
     */
    public static final String BASE_NUMBER = "0123456789";

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    /**
     * 获得一个只包含数字的字符串
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomNumbers(final int length) {
        return randomString(BASE_NUMBER, length);
    }

    /**
     * 获得一个随机的字符串
     *
     * @param baseString 随机字符选取的样本
     * @param length     字符串的长度
     * @return 随机字符串
     */
    public static String randomString(final String baseString, int length) {
        if (StringUtils.isEmpty(baseString)) {
            return StringUtils.EMPTY;
        }
        if (length < 1) {
            length = 1;
        }

        final StringBuilder sb = new StringBuilder(length);
        final int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            final int number = randomInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limitExclude 限制随机数的范围，不包括这个数
     * @return 随机数
     * @see Random#nextInt(int)
     */
    public static int randomInt(final int limitExclude) {
        return getRandom().nextInt(limitExclude);
    }
}
