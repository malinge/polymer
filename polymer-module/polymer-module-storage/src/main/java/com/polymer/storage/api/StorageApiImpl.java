package com.polymer.storage.api;

import com.polymer.api.storage.StorageApi;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.storage.service.base.AbstractStorageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * 存储服务Api
 *
 * @author polymer
 */
@Component
public class StorageApiImpl implements StorageApi {
    @Resource
    private AbstractStorageService abstractStorageService;

    /**
     * 根据文件名，生成带时间戳的新文件名
     *
     * @param fileName 文件名
     * @return 返回带时间戳的文件名
     */
    @Override
    public String getNewFileName(String fileName) {
        return abstractStorageService.getNewFileName(fileName);
    }

    /**
     * 生成路径，不包含文件名
     *
     * @return 返回生成的路径
     */
    @Override
    public String getPath() {
        return abstractStorageService.getPath();
    }

    /**
     * 根据文件名，生成路径
     *
     * @param fileName 文件名
     * @return 生成文件路径
     */
    @Override
    public String getPath(String fileName) {
        return abstractStorageService.getPath(fileName);
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回相对路径
     */
    @Override
    public String upload(byte[] data, String path) {
        return abstractStorageService.upload(data, path);
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回相对路径
     */
    @Override
    public String upload(InputStream inputStream, String path) {
        return abstractStorageService.upload(inputStream, path);
    }

    /**
     * 生成文件预签名下载地址
     *
     * @param path 文件相对路径，包括文件名
     * @return 预签名下载地址
     */
    @Override
    public String getFilePresignedDownloadUrl(String path) {
        return abstractStorageService.generatePresignedUrl(path, Boolean.TRUE);
    }

    @Override
    public String processImages(String htmlBody) {
        if (StringUtils.isBlank(htmlBody)) {
            return "";
        }
        // 解析HTML
        Document document = Jsoup.parse(htmlBody);
        // 获取所有img标签
        Elements imgElements = document.select("img");
        // 遍历所有img标签
        for (Element img : imgElements) {
            // 获取data-href属性值
            String dataHref = img.attr("data-href");
            // 如果data-href不为空，则重新构建src
            if (StringUtils.isNotBlank(dataHref)) {
                String newSrc = abstractStorageService.generatePresignedUrl(dataHref, Boolean.TRUE);
                // 更新src属性
                img.attr("src", newSrc);
            }
        }
        return document.body().html();
    }

    @Override
    public InputStream getInputStream(String path) {
        return abstractStorageService.getInputStream(path);
    }
}
