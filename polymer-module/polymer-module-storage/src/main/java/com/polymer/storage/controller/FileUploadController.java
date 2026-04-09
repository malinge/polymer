package com.polymer.storage.controller;

import com.polymer.framework.common.pojo.Result;
import com.polymer.storage.service.FileUploadService;
import com.polymer.storage.vo.SysClientFilePresignedVO;
import com.polymer.storage.vo.SysFileUploadVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传
 *
 * @author polymer
 */
@RestController
@RequestMapping("storage/file")
@Tag(name = "文件上传")
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "模式一：后端上传文件")
    public Result<SysFileUploadVO> upload(@RequestParam("file") MultipartFile file)  {
        SysFileUploadVO vo = fileUploadService.upload(file);

        return Result.ok(vo);
    }

    @GetMapping("/presigned-url/upload")
    @Operation(summary = "获取文件预签名上传地址信息", description = "模式二：前端上传文件：用于前端直接上传或下载七牛、阿里云 OSS 等文件存储器")
    public Result<SysClientFilePresignedVO> getFilePresignedUploadUrl(@RequestParam("fileName") String fileName) {
        return Result.ok(fileUploadService.getFilePresignedUploadUrl(fileName));
    }

    @GetMapping("/presigned-url/download")
    @Operation(summary = "获取文件预签名下载地址")
    public Result<String> getFilePresignedDownloadUrl(@RequestParam("path") String path) {
        return Result.ok(fileUploadService.getFilePresignedDownloadUrl(path));
    }

}
