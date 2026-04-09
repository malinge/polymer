package com.polymer.framework.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception工具类
 *
 * @author polymer
 */
public class ExceptionUtils {

    /**
     * 获取异常堆栈信息
     *
     * @param e 异常
     * @return 返回异常堆栈信息字符串
     */
    public static String getExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

}