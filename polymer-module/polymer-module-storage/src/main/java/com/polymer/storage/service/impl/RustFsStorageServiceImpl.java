package com.polymer.storage.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.MimeType;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.Optional;

/**
 * RustFs存储
 *
 * @author polymer
 */
public class RustFsStorageServiceImpl extends AbstractStorageService {
    private static final Logger log = LoggerFactory.getLogger(RustFsStorageServiceImpl.class);
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public RustFsStorageServiceImpl(StorageProperties properties) {
        this.properties = properties;

        // 构建AWS基本凭证
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                properties.getRustfs().getAccessKey(),
                properties.getRustfs().getSecretKey()
        );

        // 构建S3客户端
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(properties.getRustfs().getEndPoint())) // RustFS address
                .region(Region.US_EAST_1) // RustFS does not validate regions
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .forcePathStyle(true) // Required for RustFS compatibility
                .build();

        // 构建S3预签名器
        this.s3Presigner = S3Presigner.builder()
                .endpointOverride(URI.create(properties.getRustfs().getEndPoint()))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

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
            // 获取内容类型
            String contentType = getContentType(path);

            // 读取输入流数据
            byte[] data;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                data = outputStream.toByteArray();
            }

            // 构建PutObject请求
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(properties.getRustfs().getBucketName())
                    .key(path)
                    .contentType(contentType)
                    .contentLength((long) data.length)
                    .build();

            // 执行上传
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(data));
        } catch (Exception e) {
            throw new ServiceException("上传文件失败：", e);
        }

        return path;
    }

    /**
     * 获取文件内容类型
     */
    private String getContentType(String path) {
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(path);
        return mediaType.map(MimeType::toString).orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * 初始化存储桶（如果不存在则创建）
     */
    private void initializeBucket() {
        try {
            // 检查存储桶是否存在
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(properties.getRustfs().getBucketName())
                    .build();

            try {
                s3Client.headBucket(headBucketRequest);
                log.info("RustFS存储桶已存在: {}", properties.getRustfs().getBucketName());
            } catch (NoSuchBucketException e) {
                // 存储桶不存在，创建存储桶
                CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                        .bucket(properties.getRustfs().getBucketName())
                        .build();

                s3Client.createBucket(createBucketRequest);
                log.info("RustFS存储桶创建成功: {}", properties.getRustfs().getBucketName());
            }
        } catch (Exception e) {
            log.error("初始化RustFS存储桶失败", e);
            throw new ServiceException("初始化RustFS存储桶失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String generatePresignedUrl(String path, Boolean isDownload) {
        try {
            // 过期时间（10分钟）
            Duration expiration = Duration.ofMinutes(10);

            if (isDownload) {
                // 生成下载预签名URL
                return generateDownloadPresignedUrl(path, expiration);
            } else {
                // 生成上传预签名URL
                return generateUploadPresignedUrl(path, expiration);
            }

        } catch (Exception e) {
            log.error("生成RustFS预签名URL失败，路径: {}", path, e);
            if (e instanceof S3Exception) {
                S3Exception s3Exception = (S3Exception) e;
                throw new ServiceException("生成预签名URL失败: " + s3Exception.awsErrorDetails().errorMessage(), e);
            }
            throw new ServiceException("生成RustFS预签名URL失败：" + e.getMessage(), e);
        }
    }

    /**
     * 生成下载预签名URL
     */
    private String generateDownloadPresignedUrl(String key, Duration expiration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(properties.getRustfs().getBucketName())
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(expiration)
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }

    /**
     * 生成上传预签名URL
     */
    private String generateUploadPresignedUrl(String key, Duration expiration) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(properties.getRustfs().getBucketName())
                .key(key)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(expiration)
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        return presignedRequest.url().toString();
    }
}
