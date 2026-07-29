package com.polymer.framework.common.utils;

import com.polymer.framework.common.core.service.StorageService;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 图片处理工具类
 *
 * @author polymer
 */
public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    private static final int CONNECT_TIMEOUT = 30 * 1000;
    private static final int READ_TIMEOUT = 60 * 1000;

    private ImageUtils() {
        // 工具类私有构造方法，防止实例化
    }

    /**
     * 获取图片字节数组
     *
     * @param imagePath 图片路径（支持HTTP/HTTPS网络地址或StorageService存储路径）
     * @return 图片字节数组，加载失败返回null
     */
    public static byte[] getImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            log.warn("图片路径为空");
            return null;
        }

        try (InputStream is = getFile(imagePath)) {
            if (is == null) {
                log.warn("无法获取图片输入流, path: {}", imagePath);
                return null;
            }
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            log.error("图片加载异常, path: {}, error: {}", imagePath, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取图片输入流
     *
     * @param imagePath 图片路径（支持HTTP/HTTPS网络地址或StorageService存储路径）
     * @return 图片输入流，获取失败返回null
     */
    public static InputStream getFile(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            log.warn("图片路径为空");
            return null;
        }

        try {
            byte[] result = readFile(imagePath);
            if (result == null || result.length == 0) {
                log.warn("读取图片数据为空, path: {}", imagePath);
                return null;
            }
            return new ByteArrayInputStream(result);
        } catch (Exception e) {
            log.error("获取图片异常, path: {}, error: {}", imagePath, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 读取文件为字节数据
     *
     * @param path 文件路径（支持HTTP/HTTPS网络地址或StorageService存储路径）
     * @return 字节数据，读取失败返回null
     */
    public static byte[] readFile(String path) {
        if (path == null || path.trim().isEmpty()) {
            log.warn("文件路径为空");
            return null;
        }

        if (path.startsWith("http://") || path.startsWith("https://")) {
            return readFromUrl(path);
        } else {
            return readFromStorage(path);
        }
    }

    /**
     * 从网络URL读取文件
     */
    private static byte[] readFromUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setDoInput(true);

            try (InputStream in = connection.getInputStream()) {
                return IOUtils.toByteArray(in);
            }
        } catch (IOException e) {
            log.error("从网络地址读取文件异常, url: {}, error: {}", urlStr, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 从存储服务读取文件
     */
    private static byte[] readFromStorage(String path) {
        StorageService storageService = SpringUtils.getBean(StorageService.class);
        try {
            InputStream in = storageService.getInputStream(path);
            if (in == null) {
                log.warn("存储服务返回空输入流, path: {}", path);
                return null;
            }
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("从存储服务读取文件异常, path: {}, error: {}", path, e.getMessage());
            return null;
        }
    }
}