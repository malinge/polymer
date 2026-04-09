package com.polymer.storage.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

/**
 * Minio存储
 *
 * @author polymer
 */
public class MinioStorageServiceImpl extends AbstractStorageService {
    private static final Logger log = LoggerFactory.getLogger(MinioStorageServiceImpl.class);
    private final MinioClient minioClient;

    public MinioStorageServiceImpl(StorageProperties properties) {
        this.properties = properties;

        // 构建minio客户端
        minioClient = MinioClient.builder().endpoint(properties.getMinio().getEndPoint())
                .credentials(properties.getMinio().getAccessKey(), properties.getMinio().getSecretKey()).build();

        // 初始化存储桶
        initializeBucket();
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(path);
            if (mediaType.isPresent()) {
                contentType = mediaType.get().toString();
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(properties.getMinio().getBucketName())
                            .contentType(contentType)
                            .object(path)
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );

        } catch (Exception e) {
            throw new ServiceException("上传文件失败：", e);
        }
        return path;
    }

    @Override
    public String generatePresignedUrl(String path, Boolean isDownload) {
        // 替换您的过期时间，单位是秒 （10分钟）
        long expireSeconds = 600L;
        String presignedUrl;
        try {
            // 设置HTTP方法（GET用于下载，PUT用于上传）
            Method method = isDownload ? Method.GET : Method.PUT;

            // 生成预签名URL
            presignedUrl = minioClient.getPresignedObjectUrl(
                    io.minio.GetPresignedObjectUrlArgs.builder()
                            .method(method)
                            .bucket(properties.getMinio().getBucketName())
                            .object(path)
                            .expiry((int) expireSeconds)
                            .build());
        } catch (Exception e) {
            log.error("Error generating presigned URL for object: {}", path, e);
            throw new ServiceException("Failed to generate presigned URL", e);
        }
        // 生成预签名URL
        return presignedUrl;
    }

    /**
     * 初始化存储桶（如果不存在则创建）
     */
    private void initializeBucket() {
        try {
            //如果BucketName不存在，则创建
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getMinio().getBucketName()).build());

            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getMinio().getBucketName()).build());
            }
        } catch (Exception e) {
            log.error("初始化Minio存储桶失败", e);
            throw new ServiceException("初始化Minio存储桶失败: " + e.getMessage(), e);
        }
    }
}
