package com.polymer.framework.common.core.service;

import java.io.InputStream;

public interface StorageService {
    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param fileName 文件名
     * @return 返回相对路径
     */
    String upload(byte[] data, String fileName);

    InputStream getInputStream(String path);
}
