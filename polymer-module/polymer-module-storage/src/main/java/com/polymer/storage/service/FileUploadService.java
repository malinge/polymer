package com.polymer.storage.service;

import com.polymer.storage.vo.SysClientFilePresignedVO;
import com.polymer.storage.vo.SysFileUploadVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * 上传文件
     * @param file 文件
     * @return SysFileUploadVO
     */
    SysFileUploadVO upload(MultipartFile file);

    /**
     * 生成文件预签名地址信息
     *
     * @param fileName 文件名（带后缀）
     * @return 预签名地址信息
     */
    SysClientFilePresignedVO getFilePresignedUploadUrl(String fileName);

    /**
     * 生成文件预签名下载地址
     *
     * @param path 文件相对路径，包括文件名
     * @return 预签名下载地址
     */
    String getFilePresignedDownloadUrl(String path);
}
