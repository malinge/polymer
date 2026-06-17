package com.polymer.generator.vo;

/**
 * 预览视图对象
 *
 * @author xiangmeng
 */

public class PreviewVO {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件内容
     */
    private String content;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}