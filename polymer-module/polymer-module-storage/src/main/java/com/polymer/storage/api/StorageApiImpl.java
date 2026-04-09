package com.polymer.storage.api;

import com.polymer.api.storage.StorageApi;
import com.polymer.storage.service.base.AbstractStorageService;
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
     * @return 返回http地址
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
     * @return 返回http地址
     */
    @Override
    public String upload(InputStream inputStream, String path) {
        return abstractStorageService.upload(inputStream, path);
    }
}
