package com.polymer.storage.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云存储
 *
 * @author polymer
 */
public class AliyunStorageServiceImpl extends AbstractStorageService {
    private static final Logger log = LoggerFactory.getLogger(AliyunStorageServiceImpl.class);
    public AliyunStorageServiceImpl(StorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        OSS client = new OSSClientBuilder().build(properties.getAliyun().getEndPoint(),
                properties.getAliyun().getAccessKeyId(), properties.getAliyun().getAccessKeySecret());
        try {
            client.putObject(properties.getAliyun().getBucketName(), path, inputStream);
        } catch (Exception e) {
            throw new ServiceException("上传文件失败：", e);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }

        return path;
    }

    @Override
    public String generatePresignedUrl(String path, Boolean isDownload) {
        OSS ossClient = new OSSClientBuilder().build(properties.getAliyun().getEndPoint(),
                properties.getAliyun().getAccessKeyId(), properties.getAliyun().getAccessKeySecret());
        URL signedUrl;
        try {
            // 设置预签名URL过期时间（10分钟）
            Date expiration = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
            // 设置 HTTP 方法
            HttpMethod method = isDownload ? HttpMethod.GET : HttpMethod.PUT;

            // 生成预签名URL。
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(properties.getAliyun().getBucketName(), path, method);
            // 设置过期时间。
            request.setExpiration(expiration);
            // 通过HTTP PUT请求生成预签名URL。
            signedUrl = ossClient.generatePresignedUrl(request);
            // 打印预签名URL。
            log.info("signed url for putObject: {}", signedUrl);
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:{}", oe.getErrorMessage());
            log.error("Error Code:{}", oe.getErrorCode());
            log.error("Request ID:{}", oe.getRequestId());
            log.error("Host ID:{}", oe.getHostId());
            throw new ServiceException("生成预签名URL失败："+oe.getMessage());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            throw new ServiceException("生成预签名URL失败："+ce.getMessage());
        }
        return signedUrl.toString();
    }

}
