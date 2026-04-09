package com.polymer.generator.config.template;

import java.util.List;

/**
 * 代码生成信息
 * polymer@126.com
 *
 */
public class GeneratorInfo {
    private ProjectInfo project;
    private DeveloperInfo developer;
    private List<TemplateInfo> templates;

    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    public DeveloperInfo getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperInfo developer) {
        this.developer = developer;
    }

    public List<TemplateInfo> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateInfo> templates) {
        this.templates = templates;
    }
}
