package com.polymer.generator.controller;

import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.IoUtils;
import com.polymer.generator.service.GeneratorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 * polymer@126.com
 *
 */
@Controller
@RequestMapping("generator/gen/generator")
public class GeneratorController {
    @Resource
    private GeneratorService generatorService;

    /**
     * 生成代码（zip压缩包）
     */
    @GetMapping("download")
    public void download(String tableIds, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        // 生成代码
        for (String tableId : tableIds.split(",")) {
            generatorService.downloadCode(Long.parseLong(tableId), zip);
        }

        IoUtils.close(zip);

        // zip压缩包数据
        byte[] data = outputStream.toByteArray();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"polymer.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtils.write(response.getOutputStream(), false, data);
    }

    /**
     * 生成代码（自定义目录）
     */
    @ResponseBody
    @PostMapping("code")
    public Result<String> code(@RequestBody Long[] tableIds) throws Exception {
        // 生成代码
        for (Long tableId : tableIds) {
            generatorService.generatorCode(tableId);
        }

        return Result.ok();
    }

}