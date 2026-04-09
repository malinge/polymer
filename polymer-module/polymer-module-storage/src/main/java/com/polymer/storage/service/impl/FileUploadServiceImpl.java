package com.polymer.storage.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.storage.service.FileUploadService;
import com.polymer.storage.service.base.AbstractStorageService;
import com.polymer.storage.vo.SysClientFilePresignedVO;
import com.polymer.storage.vo.SysFileUploadVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Resource
    private AbstractStorageService abstractStorageService;

    /**
     * 上传文件
     * @param file 文件
     * @return SysFileUploadVO
     */
    @Override
    public SysFileUploadVO upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException("请选择需要上传的文件");
        }
        // 上传路径
        String path = abstractStorageService.getPath(file.getOriginalFilename());
        // 上传文件
        String url;
        try {
            url = abstractStorageService.upload(file.getBytes(), path);
        } catch (IOException e) {
            throw new ServiceException("文件上传失败！");
        }

        SysFileUploadVO vo = new SysFileUploadVO();
        vo.setUrl(url);
        vo.setSize(file.getSize());
        vo.setName(file.getOriginalFilename());
        vo.setPlatform(abstractStorageService.properties.getConfig().getType().name());
        return vo;
    }

    /**
     * 生成文件预签名地址信息
     *
     * @param fileName 文件名
     * @return 预签名地址信息
     */
    @Override
    public SysClientFilePresignedVO getFilePresignedUploadUrl(String fileName) {
        SysClientFilePresignedVO vo = new SysClientFilePresignedVO();
        // 上传路径
        String path = abstractStorageService.getPath(fileName);
        vo.setPath(path);
        String presignedUrl = abstractStorageService.generatePresignedUrl(path, Boolean.FALSE);
        vo.setPresignedUrl(presignedUrl);
        vo.setPlatform(abstractStorageService.properties.getConfig().getType().name());
        return vo;

    }

    /**
     * 生成文件预签名下载地址
     *
     * @param path 文件相对路径，包括文件名
     * @return 预签名下载地址
     */
    @Override
    public String getFilePresignedDownloadUrl(String path) {
        return abstractStorageService.generatePresignedUrl(path, Boolean.TRUE);
    }
}
