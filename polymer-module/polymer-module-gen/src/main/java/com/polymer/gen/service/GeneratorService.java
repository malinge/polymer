package com.polymer.gen.service;

import com.polymer.gen.vo.PreviewVO;

import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 * polymer@126.com
 *
 */
public interface GeneratorService {

    void downloadCode(Long tableId, ZipOutputStream zip);

    void generatorCode(Long tableId);

    List<PreviewVO> preview(Long tableId);
}
