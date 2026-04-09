package com.polymer.framework.common.core.text;

import com.polymer.framework.common.utils.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符集工具类
 *
 * @author polymer
 */
public class CharsetKit {
    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * UTF-8
     */
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;


    /**
     * 转换为Charset对象
     *
     * @param charset 字符集，为空则返回默认字符集
     * @return Charset
     */
    public static Charset charset(String charset) {
        return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }
}
