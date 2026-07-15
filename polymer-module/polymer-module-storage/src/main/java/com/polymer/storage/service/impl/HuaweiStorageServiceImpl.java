package com.polymer.storage.service.impl;

import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 华为云存储
 *
 * @author polymer
 */
public class HuaweiStorageServiceImpl extends AbstractStorageService {
    private static final Logger log = LoggerFactory.getLogger(HuaweiStorageServiceImpl.class);
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

    @Override
    public InputStream getInputStream(String path) {
        ObsClient client = null;
        try {
            client = new ObsClient(properties.getHuawei().getAccessKey(),
                    properties.getHuawei().getSecretKey(), properties.getHuawei().getEndPoint());

            GetObjectRequest request = new GetObjectRequest(properties.getHuawei().getBucketName(), path);
            ObsObject obsObject = client.getObject(request);
            // 获取对象输入流
            // 注意：调用方使用完后需要关闭流，ObsClient会在流关闭后自动释放连接
            return obsObject.getObjectContent();
        } catch (Exception e) {
            if (client != null) {
                try {
                    client.close();
                } catch (Exception ex) {
                    log.error("关闭ObsClient失败", ex);
                }
            }
            log.error("获取文件流失败，路径: {}", path, e);
            throw new ServiceException("获取文件流失败: " + e.getMessage(), e);
        }
    }

}
