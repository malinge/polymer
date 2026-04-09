package com.polymer.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IoUtils {
    private static final Logger log = LoggerFactory.getLogger(IoUtils.class);

    /**
     * 将多部分内容写到流中，自动转换为UTF-8字符串
     *
     * @param out        输出流
     * @param isCloseOut 写入完毕是否关闭输出流
     * @param contents   写入的内容，调用toString()方法，不包括不会自动换行
     * @throws RuntimeException IO异常
     * @since 3.1.1
     */
    public static void writeUtf8(OutputStream out, boolean isCloseOut, Object... contents) throws IOException {
        write(out, StandardCharsets.UTF_8, isCloseOut, contents);
    }

    /**
     * 将多部分内容写到流中，自动转换为字符串
     *
     * @param out        输出流
     * @param charset    写出的内容的字符集
     * @param isCloseOut 写入完毕是否关闭输出流
     * @param contents   写入的内容，调用toString()方法，不包括不会自动换行
     * @throws RuntimeException IO异常
     * @since 3.0.9
     */
    public static void write(OutputStream out, Charset charset, boolean isCloseOut, Object... contents) throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = getWriter(out, charset);
            for (Object content : contents) {
                if (content != null) {
                    osw.write(toStr(content, ""));
                }
            }
            osw.flush();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (isCloseOut) {
                close(osw);
            }
        }
    }

    /**
     * 转换为字符串<br>
     * 如果给定的值为null，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static String toStr(Object value, String defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }

    /**
     * 获得一个Writer
     *
     * @param out     输入流
     * @param charset 字符集
     * @return OutputStreamWriter对象
     */
    public static OutputStreamWriter getWriter(OutputStream out, Charset charset) {
        if (null == out) {
            return null;
        }

        if (null == charset) {
            return new OutputStreamWriter(out);
        } else {
            return new OutputStreamWriter(out, charset);
        }
    }

    /**
     * 安全关闭 Closeable 对象 - 替换 IoUtil.close(zip)
     * @param closeable 要关闭的对象
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.warn("关闭资源时发生异常", e);
            }
        }
    }

    /**
     * 写入数据到输出流 - 替换 IoUtil.write(response.getOutputStream(), false, data)
     * @param outputStream 输出流
     * @param data 要写入的数据
     */
    public static void write(OutputStream outputStream, byte[] data) {
        write(outputStream, false, data);
    }

    /**
     * 写入数据到输出流
     * @param outputStream 输出流
     * @param closeStream 是否关闭流
     * @param data 要写入的数据
     */
    public static void write(OutputStream outputStream, boolean closeStream, byte[] data) {
        if (outputStream == null || data == null) {
            return;
        }

        try {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            log.error("写入输出流失败", e);
            throw new RuntimeException("写入输出流失败", e);
        } finally {
            if (closeStream) {
                close(outputStream);
            }
        }
    }

    /**
     * 写入字符串到输出流（UTF-8编码）
     */
    public static void write(OutputStream outputStream, String data) {
        write(outputStream, false, data);
    }

    /**
     * 写入字符串到输出流（UTF-8编码）
     */
    public static void write(OutputStream outputStream, boolean closeStream, String data) {
        if (outputStream == null || data == null) {
            return;
        }

        write(outputStream, closeStream, data.getBytes(StandardCharsets.UTF_8));
    }

}
