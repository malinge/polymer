package com.polymer.generator.common.utils;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.IoUtils;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * 模板工具类
 * polymer@126.com
 *
 */
public class TemplateUtils {
    private static final Logger log = LoggerFactory.getLogger(TemplateUtils.class);
    /**
     * 获取模板渲染后的内容
     *
     * @param content   模板内容
     * @param dataModel 数据模型
     */
    public static String getContent(String content, Map<String, Object> dataModel) {
        if (dataModel.isEmpty()) {
            return content;
        }

        StringReader reader = new StringReader(content);
        StringWriter sw = new StringWriter();
        try {
            // 渲染模板
            String templateName = dataModel.get("templateName").toString();
            Template template = new Template(templateName, reader, null, "utf-8");
            template.process(dataModel, sw);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("渲染模板失败，请检查模板语法", e);
        }

        content = sw.toString();

        IoUtils.close(reader);
        IoUtils.close(sw);

        return content;
    }
}
