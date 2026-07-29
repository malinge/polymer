package com.polymer.storage.api;

import com.polymer.api.storage.StorageApi;
import com.polymer.framework.common.core.service.StorageService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

@Component
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageApi storageApi;
    @Override
    public String upload(byte[] data, String fileName) {
        String path = storageApi.getPath(fileName);
        return storageApi.upload(data, path);
    }

    @Override
    public InputStream getInputStream(String path) {
        return storageApi.getInputStream(path);
    }
}
