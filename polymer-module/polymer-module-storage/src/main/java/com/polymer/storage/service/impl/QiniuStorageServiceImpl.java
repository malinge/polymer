package com.polymer.storage.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云存储
 *
 * @author polymer
 */
public class QiniuStorageServiceImpl extends AbstractStorageService {
    private final UploadManager uploadManager;

    /**
     * 创建Auth实例
     */
    private Auth createAuth() {
        return Auth.create(properties.getQiniu().getAccessKey(), properties.getQiniu().getSecretKey());
    }

    public QiniuStorageServiceImpl(StorageProperties properties) {
        this.properties = properties;

        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            String token = Auth.create(properties.getQiniu().getAccessKey(), properties.getQiniu().getSecretKey()).
                    uploadToken(properties.getQiniu().getBucketName());
            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                throw new ServiceException(res.toString());
            }

            return path;
        } catch (Exception e) {
            throw new ServiceException("上传文件失败：", e);
        }
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new ServiceException("上传文件失败：", e);
        }
    }

    @Override
    public String generatePresignedUrl(String path, Boolean isDownload) {
        Auth auth = createAuth();
        // 转换为秒
        long expireSeconds = 600L;

        // 七牛云生成下载预签名URL
        String publicUrl = properties.getConfig().getDomain() + "/" + path;

        // 生成预签名URL
        String signedUrl = auth.privateDownloadUrl(publicUrl, expireSeconds);

        // 如果是上传，七牛云需要不同的处理方式
        if (!isDownload) {
            // 七牛云的上传凭证需要使用uploadToken
            // 这里返回的是上传凭证，客户端需要使用这个token进行上传
            return auth.uploadToken(properties.getQiniu().getBucketName(), path, expireSeconds, null, true);
        }

        return signedUrl;
    }

}
