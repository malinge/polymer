package com.polymer.generator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.FileUtils;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.common.utils.ZipUtil;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.common.utils.ProjectUtils;
import com.polymer.generator.entity.ProjectModifyEntity;
import com.polymer.generator.mapper.ProjectModifyMapper;
import com.polymer.generator.service.ProjectModifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名变更
 * polymer@126.com
 */
@Service
public class ProjectModifyServiceImpl implements ProjectModifyService {
    @Resource
    private ProjectModifyMapper projectModifyMapper;

    @Override
    public PageResult<ProjectModifyEntity> page(Query query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<ProjectModifyEntity> entityList = projectModifyMapper.selectProjectModifyList(query);
        PageInfo<ProjectModifyEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(entityList, pageInfo.getTotal());
    }

    @Override
    public byte[] download(ProjectModifyEntity project) throws IOException {
        // 原项目根路径
        File srcRoot = new File(project.getProjectPath());

        // 临时项目根路径
        File destRoot = new File(ProjectUtils.getTmpDirPath(project.getModifyProjectName()));

        // 排除的文件
        List<String> exclusions = StringUtils.splitToList(project.getExclusions(), ProjectUtils.SPLIT);

        // 获取替换规则
        Map<String, String> replaceMap = getReplaceMap(project);

        // 拷贝项目到新路径，并替换路径和文件名
        ProjectUtils.copyDirectory(srcRoot, destRoot, exclusions, replaceMap);

        // 需要替换的文件后缀
        List<String> suffixList = StringUtils.splitToList(project.getModifySuffix(), ProjectUtils.SPLIT);

        // 替换文件内容数据
        ProjectUtils.contentFormat(destRoot, suffixList, replaceMap);

        // 生成zip文件
        File zipFile = ZipUtil.zip(destRoot);

        byte[] data = FileUtils.readBytes(zipFile);

        // 清空文件
        FileUtils.clean(destRoot.getParentFile().getParentFile());

        return data;
    }

    /**
     * 获取替换规则
     */
    private Map<String, String> getReplaceMap(ProjectModifyEntity project) {
        Map<String, String> map = new LinkedHashMap<>();

        // 项目路径替换
        String srcPath = "src/main/java/" + project.getProjectPackage().replaceAll("\\.", "/");
        String destPath = "src/main/java/" + project.getModifyProjectPackage().replaceAll("\\.", "/");
        map.put(srcPath, destPath);

        // 项目包名替换
        map.put(project.getProjectPackage(), project.getModifyProjectPackage());

        // 项目标识替换
        map.put(project.getProjectCode(), project.getModifyProjectCode());
        map.put(StringUtils.upperFirst(project.getProjectCode()), StringUtils.upperFirst(project.getModifyProjectCode()));

        return map;
    }

    @Override
    public ProjectModifyEntity save(ProjectModifyEntity entity) {
        entity.setExclusions(ProjectUtils.EXCLUSIONS);
        entity.setModifySuffix(ProjectUtils.MODIFY_SUFFIX);
        entity.setCreateTime(LocalDateTime.now());
        projectModifyMapper.insertProjectModify(entity);
        return entity;
    }

    @Override
    public ProjectModifyEntity getById(Long id) {
        return projectModifyMapper.selectProjectModifyById(id);
    }

    @Override
    public ProjectModifyEntity updateById(ProjectModifyEntity entity) {
        projectModifyMapper.updateProjectModify(entity);
        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (entity.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }

        // 执行更新
        int rows = projectModifyMapper.updateProjectModify(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        ProjectModifyEntity updatedEntity = projectModifyMapper.selectProjectModifyById(entity.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return updatedEntity;
    }

    @Override
    public void removeByIds(List<Long> idList) {
        for(Long id: idList){
            projectModifyMapper.deleteProjectModifyById(id);
        }
    }
}