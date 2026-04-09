package com.polymer.storage.config;

import com.polymer.storage.enums.StorageTypeEnum;
import com.polymer.storage.properties.StorageProperties;
import com.polymer.storage.service.base.AbstractStorageService;
import com.polymer.storage.service.impl.AliyunStorageServiceImpl;
import com.polymer.storage.service.impl.HuaweiStorageServiceImpl;
import com.polymer.storage.service.impl.LocalStorageServiceImpl;
import com.polymer.storage.service.impl.MinioStorageServiceImpl;
import com.polymer.storage.service.impl.QiniuStorageServiceImpl;
import com.polymer.storage.service.impl.RustFsStorageServiceImpl;
import com.polymer.storage.service.impl.TencentStorageServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储配置文件
 *
 * @author polymer
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(prefix = "polymer.storage", value = "enabled")
public class StorageConfiguration {

    @Bean
    public AbstractStorageService storageService(StorageProperties properties) {
        if (properties.getConfig().getType() == StorageTypeEnum.LOCAL) {
            return new LocalStorageServiceImpl(properties);
        } else if (properties.getConfig().getType() == StorageTypeEnum.ALIYUN) {
            return new AliyunStorageServiceImpl(properties);
        } else if (properties.getConfig().getType() == StorageTypeEnum.TENCENT) {
            return new TencentStorageServiceImpl(properties);
        } else if (properties.getConfig().getType() == StorageTypeEnum.QINIU) {
            return new QiniuStorageServiceImpl(properties);
        } else if (properties.getConfig().getType() == StorageTypeEnum.HUAWEI) {
            return new HuaweiStorageServiceImpl(properties);
        } else if (properties.getConfig().getType() == StorageTypeEnum.MINIO) {
            return new MinioStorageServiceImpl(properties);
        }else if (properties.getConfig().getType() == StorageTypeEnum.RUSTFS) {
            return new RustFsStorageServiceImpl(properties);
        }

        return null;
    }

}
