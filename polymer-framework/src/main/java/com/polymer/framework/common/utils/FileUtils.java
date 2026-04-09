package com.polymer.framework.common.utils;

import com.polymer.framework.common.core.text.Convert;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 文件处理工具类
 *
 * @author polymer
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @return 目标文件
     */
    public static String writeImportBytes(byte[] data) {
        return "";
    }

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        String contentDispositionValue = "attachment; filename=" +
                percentEncodedFileName +
                ";" +
                "filename*=" +
                "utf-8''" +
                percentEncodedFileName;

        response.setHeader("Content-Disposition", contentDispositionValue);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * 获取文件名称 /profile/upload/2022/04/16/ruoyi.png -- ruoyi.png
     *
     * @param fileName 路径名称
     * @return 没有文件路径的名称
     */
    public static String getName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int lastUnixPos = fileName.lastIndexOf('/');
        int lastWindowsPos = fileName.lastIndexOf('\\');
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return fileName.substring(index + 1);
    }

    /**
     * 获取不带后缀文件名称 /profile/upload/2022/04/16/ruoyi.png -- ruoyi
     *
     * @param fileName 路径名称
     * @return 没有文件路径和后缀的名称
     */
    public static String getNameNotSuffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        return FilenameUtils.getBaseName(fileName);
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
                    osw.write(Convert.toStr(content, ""));
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
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeable 被关闭的对象
     */
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    /**
     * 获得一个Writer
     *
     * @param out     输入流
     * @param charset 字符集
     * @return OutputStreamWriter对象
     */
    public static OutputStreamWriter getWriter(OutputStream out, Charset charset) {
        return IoUtils.getWriter(out, charset);
    }


    /**
     * 使用 Java NIO Files 替换
     * @param content 要写入的内容
     * @param path 文件路径
     */
    public static void writeUtf8String(String content, String path) throws IOException {
        writeUtf8String(content, Paths.get(path));
    }

    public static void writeUtf8String(String content, Path path) throws IOException {
        if (content == null || path == null) {
            return;
        }

        // 确保父目录存在
        Path parent = path.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        // 写入文件，覆盖模式
        Files.write(path, Collections.singletonList(content), StandardCharsets.UTF_8);

        // 或者使用这种方式（效果相同）：
        // Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 读取文件为字节数组 - 替换 FileUtil.readBytes(zipFile)
     * @param file 要读取的文件
     * @return 文件的字节数组
     */
    public static byte[] readBytes(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("文件不存在: " + file);
        }

        if (!file.isFile()) {
            throw new IllegalArgumentException("路径不是文件: " + file);
        }

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            logger.error("读取文件失败: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("读取文件失败: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * 清空目录 - 替换 FileUtil.clean(directory)
     * @param directory 要清空的目录
     */
    public static void clean(File directory) {
        if (directory == null || !directory.exists()) {
            return;
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("路径不是目录: " + directory);
        }

        try {
            // 使用 NIO 方式清空目录
            Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    logger.debug("删除文件: {}", file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    // 不删除根目录，只删除子目录
                    if (!dir.equals(directory.toPath())) {
                        Files.delete(dir);
                        logger.debug("删除目录: {}", dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            logger.info("目录清空成功: {}", directory.getAbsolutePath());
        } catch (IOException e) {
            logger.error("清空目录失败: {}", directory.getAbsolutePath(), e);
            throw new RuntimeException("清空目录失败: " + directory.getAbsolutePath(), e);
        }
    }

    /**
     * 复制文件 - 替换 FileUtil.copyFile(srcFile, destFile)
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @return 复制成功返回true，否则返回false
     */
    public static boolean copyFile(File srcFile, File destFile) {
        if (srcFile == null || destFile == null) {
            logger.warn("复制文件失败：源文件或目标文件为空");
            return false;
        }

        if (!srcFile.exists()) {
            logger.warn("复制文件失败：源文件不存在 - {}", srcFile.getAbsolutePath());
            return false;
        }

        if (!srcFile.isFile()) {
            logger.warn("复制文件失败：源文件不是文件类型 - {}", srcFile.getAbsolutePath());
            return false;
        }

        try {
            // 确保目标目录存在
            File parentDir = destFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    logger.error("创建目标目录失败：{}", parentDir.getAbsolutePath());
                    return false;
                }
            }

            // 使用 NIO Files.copy 方法（推荐）
            Files.copy(srcFile.toPath(), destFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES);

            logger.debug("文件复制成功：{} -> {}", srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            return true;

        } catch (IOException e) {
            logger.error("文件复制失败：{} -> {}", srcFile.getAbsolutePath(), destFile.getAbsolutePath(), e);
            return false;
        }
    }

    /**
     * 递归遍历目录并过滤文件 - 替换 FileUtil.loopFiles
     * @param rootFile 根目录
     * @param filter 文件过滤器
     * @return 符合条件的文件列表
     */
    public static List<File> loopFiles(File rootFile, Predicate<File> filter) {
        List<File> result = new ArrayList<>();

        if (rootFile == null || !rootFile.exists()) {
            return result;
        }

        if (!rootFile.isDirectory()) {
            if (filter == null || filter.test(rootFile)) {
                result.add(rootFile);
            }
            return result;
        }

        try {
            Files.walkFileTree(rootFile.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    File currentFile = file.toFile();
                    if (filter == null || filter.test(currentFile)) {
                        result.add(currentFile);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    logger.warn("访问文件失败: {}", file, exc);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error("遍历目录失败: {}", rootFile.getAbsolutePath(), e);
        }

        return result;
    }


    /**
     * 读取UTF-8文本文件的所有行 - 替换 FileUtil.readUtf8Lines
     * @param file 要读取的文件
     * @return 文件内容行列表
     */
    public static List<String> readUtf8Lines(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return new ArrayList<>();
        }

        try {
            return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("读取文件行失败: {}", file.getAbsolutePath(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 写入UTF-8文本文件的所有行 - 替换 FileUtil.writeUtf8Lines
     * @param lines 要写入的行列表
     * @param file 目标文件
     * @return 写入成功返回true，否则返回false
     */
    public static boolean writeUtf8Lines(List<String> lines, File file) {
        if (lines == null || file == null) {
            return false;
        }

        try {
            // 确保父目录存在
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    logger.error("创建父目录失败: {}", parentDir.getAbsolutePath());
                    return false;
                }
            }

            Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
            logger.debug("写入文件行成功: {} ({}行)", file.getAbsolutePath(), lines.size());
            return true;

        } catch (IOException e) {
            logger.error("写入文件行失败: {}", file.getAbsolutePath(), e);
            return false;
        }
    }

    /**
     * 获取系统临时目录路径 - 替换 FileUtil.getTmpDirPath()
     * @return 系统临时目录路径
     */
    public static String getTmpDirPath() {
        return System.getProperty("java.io.tmpdir");
    }

}

