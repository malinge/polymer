package com.polymer.framework.common.utils;

import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Locale;

/**
 * 文件类型工具类
 *
 * @author polymer
 */
public class FileTypeUtils {


    /**
     * 获取文件后缀名（不带点） - 替换 FileNameUtil.getSuffix(file)
     * @param file 文件对象
     * @return 文件后缀名，如 "jpg", "pdf" 等
     */
    public static String getSuffix(File file) {
        if (file == null) {
            return "";
        }
        return getSuffix(file.getName());
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: ruoyi.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getSuffix(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return "";
        }

        // 处理以点开头的隐藏文件
        if (fileName.startsWith(".") && fileName.indexOf('.', 1) == -1) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDotIndex + 1).toLowerCase(Locale.ROOT);
    }

    /**
     * 获取文件类型
     *
     * @param photoByte 文件字节码
     * @return 后缀（不含".")
     */
    public static String getFileExtendName(byte[] photoByte) {
        String strFileExtendName = "JPG";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97)) {
            strFileExtendName = "GIF";
        } else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70)) {
            strFileExtendName = "JPG";
        } else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
            strFileExtendName = "BMP";
        } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
            strFileExtendName = "PNG";
        }
        return strFileExtendName;
    }
}