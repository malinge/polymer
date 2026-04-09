package com.polymer.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 压缩目录 - 替换 Hutool 的 ZipUtil.zip(destRoot)
     * @param destRoot 要压缩的目录
     * @return 压缩后的zip文件
     */
    public static File zip(File destRoot) {
        if (destRoot == null || !destRoot.exists() || !destRoot.isDirectory()) {
            throw new IllegalArgumentException("源目录不存在或不是目录: " + destRoot);
        }

        String zipFilePath = destRoot.getAbsolutePath() + ".zip";
        return zip(destRoot, new File(zipFilePath));
    }

    /**
     * 压缩目录到指定文件
     * @param sourceDir 源目录
     * @param zipFile 目标zip文件
     * @return 压缩后的zip文件
     */
    public static File zip(File sourceDir, File zipFile) {
        try {
            validateParameters(sourceDir, zipFile);

            // 确保输出文件的父目录存在
            File parentDir = zipFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
                compressDirectory(sourceDir.toPath(), sourceDir.toPath(), zos);
            }

            logger.info("目录压缩成功: {} -> {}", sourceDir.getAbsolutePath(), zipFile.getAbsolutePath());
            return zipFile;

        } catch (IOException e) {
            logger.error("目录压缩失败: {}", sourceDir.getAbsolutePath(), e);
            throw new RuntimeException("压缩目录失败: " + sourceDir.getAbsolutePath(), e);
        }
    }

    /**
     * 参数验证
     */
    private static void validateParameters(File sourceDir, File zipFile) {
        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("源目录不存在: " + sourceDir);
        }

        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("源路径不是目录: " + sourceDir);
        }

        if (zipFile == null) {
            throw new IllegalArgumentException("目标zip文件不能为null");
        }
    }

    /**
     * 递归压缩目录
     */
    private static void compressDirectory(Path rootDir, Path currentDir, ZipOutputStream zos) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDir)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    // 添加空目录条目（确保空目录也被压缩）
                    addDirectoryEntry(rootDir, entry, zos);
                    // 递归处理子目录
                    compressDirectory(rootDir, entry, zos);
                } else {
                    // 压缩文件
                    addFileToZip(rootDir, entry, zos);
                }
            }
        }
    }

    /**
     * 添加文件到zip
     */
    private static void addFileToZip(Path rootDir, Path file, ZipOutputStream zos) throws IOException {
        String entryName = getRelativeEntryName(rootDir, file);

        ZipEntry zipEntry = new ZipEntry(entryName);
        zipEntry.setTime(Files.getLastModifiedTime(file).toMillis());

        zos.putNextEntry(zipEntry);
        Files.copy(file, zos);
        zos.closeEntry();

        logger.debug("已压缩文件: {}", entryName);
    }

    /**
     * 添加空目录条目
     */
    private static void addDirectoryEntry(Path rootDir, Path dir, ZipOutputStream zos) throws IOException {
        String entryName = getRelativeEntryName(rootDir, dir) + "/";

        // 跳过根目录
        if (!entryName.equals("/")) {
            ZipEntry zipEntry = new ZipEntry(entryName);
            zipEntry.setTime(Files.getLastModifiedTime(dir).toMillis());

            zos.putNextEntry(zipEntry);
            zos.closeEntry();
        }
    }

    /**
     * 获取相对路径作为zip条目名称
     */
    private static String getRelativeEntryName(Path rootDir, Path file) {
        return rootDir.relativize(file).toString().replace("\\", "/");
    }
}
