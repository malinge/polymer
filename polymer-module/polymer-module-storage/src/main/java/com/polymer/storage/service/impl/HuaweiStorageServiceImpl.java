package com.polymer.storage.service.impl;

import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 华为云存储
 *
 * @author polymer
 */
public class HuaweiStorageServiceImpl extends AbstractStorageService {

    public HuaweiStorageServiceImpl(StorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        ObsClient client = new ObsClient(properties.getHuawei().getAccessKey(),
                properties.getHuawei().getSecretKey(), properties.getHuawei().getEndPoint());
        try {
            client.putObject(properties.getHuawei().getBucketName(), path, inputStream);
            client.close();
        } catch (Exception e) {
            throw new ServiceException("上传文件失败：", e);
        }

        return path;
    }

    @Override
    public String generatePresignedUrl(String path, Boolean isDownload) {
        ObsClient obsClient = new ObsClient(properties.getHuawei().getAccessKey(),
                properties.getHuawei().getSecretKey(), properties.getHuawei().getEndPoint());

        // 替换您的过期时间，单位是秒 （10分钟）
        long expireSeconds = 600L;
        // 设置 HTTP 方法
        HttpMethodEnum method = isDownload ? HttpMethodEnum.GET : HttpMethodEnum.PUT;
        // 替换成您对应的操作
        TemporarySignatureRequest request = new TemporarySignatureRequest(method, expireSeconds);
        // 替换为请求本次操作访问的桶名和对象名
        request.setBucketName("bucketname");
        request.setObjectKey("objectname");
        return obsClient.createTemporarySignature(request).getSignedUrl();
    }

}
