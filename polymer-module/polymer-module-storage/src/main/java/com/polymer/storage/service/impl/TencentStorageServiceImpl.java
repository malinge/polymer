package com.polymer.storage.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 腾讯云存储
 *
 * @author polymer
 */
public class TencentStorageServiceImpl extends AbstractStorageService {
    private final COSCredentials cred;
    private final ClientConfig clientConfig;

    public TencentStorageServiceImpl(StorageProperties properties) {
        this.properties = properties;

        cred = new BasicCOSCredentials(properties.getTencent().getAccessKey(), properties.getTencent().getSecretKey());

        clientConfig = new ClientConfig(new Region(properties.getTencent().getRegion()));
        clientConfig.setHttpProtocol(HttpProtocol.https);
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            COSClient cosClient = new COSClient(cred, clientConfig);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available());

            PutObjectRequest request = new PutObjectRequest(properties.getTencent().getBucketName(), path, inputStream, metadata);
            PutObjectResult result = cosClient.putObject(request);

            cosClient.shutdown();
            if (result.getETag() == null) {
                throw new ServiceException("上传文件失败，请检查配置信息");
            }
        } catch (Exception e) {
            throw new ServiceException("上传文件失败：", e);
        }

        return path;
    }

    @Override
    public String generatePresignedUrl(String path, Boolean isDownload) {
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 设置预签名URL过期时间（10分钟）
        Date expiration = new Date(System.currentTimeMillis() + 10 * 60 * 1000);

        // 设置 HTTP 方法
        HttpMethodName method = isDownload ? HttpMethodName.GET : HttpMethodName.PUT;

        // 生成预签名 URL
        URL url = cosClient.generatePresignedUrl(properties.getTencent().getBucketName(), path, expiration, method);
        return url.toString();
    }

    @Override
    public InputStream getInputStream(String path) {
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            // 构建GetObject请求
            GetObjectRequest getObjectRequest = new GetObjectRequest(properties.getTencent().getBucketName(), path);

            // 获取对象
            COSObject cosObject = cosClient.getObject(getObjectRequest);

            // 获取对象输入流

            // 注意：调用方使用完后需要关闭流，COSClient会通过流关闭自动释放连接
            return cosObject.getObjectContent();
        } catch (Exception e) {
            cosClient.shutdown();
            throw new ServiceException("获取文件流失败: " + e.getMessage(), e);
        }
    }

}
