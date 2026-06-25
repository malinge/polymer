package com.polymer.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.api.storage.StorageApi;
import com.polymer.api.system.SysAttachmentApi;
import com.polymer.api.system.dto.SysAttachmentDTO;
import com.polymer.demo.entity.DemoMultipleFilesEntity;
import com.polymer.demo.mapper.DemoMultipleFilesMapper;
import com.polymer.demo.query.DemoMultipleFilesQuery;
import com.polymer.demo.service.DemoMultipleFilesService;
import com.polymer.demo.vo.DemoMultipleFilesVO;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多文件上传样例Service业务层处理
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-06-23
 */
@Service
public class DemoMultipleFilesServiceImpl implements DemoMultipleFilesService {
    @Resource
    private DemoMultipleFilesMapper demoMultipleFilesMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;
    @Resource
    private SysAttachmentApi sysAttachmentApi;
    @Resource
    private StorageApi storageApi;

    /**
     * 查询多文件上传样例分页列表
     *
     * @param query 查询条件
     * @return 多文件上传样例分页集合
     */
    @Override
    public PageResult<DemoMultipleFilesVO> page(DemoMultipleFilesQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DemoMultipleFilesEntity> entityList = demoMultipleFilesMapper.selectDemoMultipleFilesList(query);
        PageInfo<DemoMultipleFilesEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, DemoMultipleFilesVO::new), pageInfo.getTotal());
    }

    /**
     * 查询多文件上传样例
     *
     * @param id 多文件上传样例主键
     * @return 多文件上传样例
     */
    @Override
    public DemoMultipleFilesVO selectDemoMultipleFilesById(Long id){
        DemoMultipleFilesEntity entity =  demoMultipleFilesMapper.selectDemoMultipleFilesById(id);
        DemoMultipleFilesVO demoMultipleFilesVO = ConvertUtils.convertTo(entity, DemoMultipleFilesVO::new);

        String newDes = storageApi.processImages(demoMultipleFilesVO.getDescription());
        demoMultipleFilesVO.setDescription(newDes);

        List<SysAttachmentDTO> images = sysAttachmentApi.findListByBizMark(id, "img" + DemoMultipleFilesVO.class.getName());
        List<SysAttachmentDTO> attachments = sysAttachmentApi.findListByBizMark(id, "file" + DemoMultipleFilesVO.class.getName());
        demoMultipleFilesVO.setImages(images);
        demoMultipleFilesVO.setAttachments(attachments);
        return demoMultipleFilesVO;
    }

    /**
     * 新增多文件上传样例
     *
     * @param vo 多文件上传样例
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DemoMultipleFilesVO insertDemoMultipleFiles(DemoMultipleFilesVO vo){

        // 1. VO 转 Entity
        DemoMultipleFilesEntity entity = ConvertUtils.convertTo(vo, DemoMultipleFilesEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        demoMultipleFilesMapper.insertDemoMultipleFiles(entity);
        sysAttachmentApi.saveBatch(entity.getId(), "img"+ DemoMultipleFilesVO.class.getName(), vo.getImages());
        sysAttachmentApi.saveBatch(entity.getId(), "file"+ DemoMultipleFilesVO.class.getName(), vo.getAttachments());
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, DemoMultipleFilesVO::new);
    }

    /**
     * 批量新增多文件上传样例
     *
     * @param list 多文件上传样例集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsertDemoMultipleFiles(List<DemoMultipleFilesVO> list){
        List<DemoMultipleFilesEntity> entityList = ConvertUtils.convertListTo(list, DemoMultipleFilesEntity::new);
        return batchUtils.executeBatch(DemoMultipleFilesMapper.class, entityList, DemoMultipleFilesMapper::insertDemoMultipleFiles);
    }

    /**
     * 修改多文件上传样例
     *
     * @param vo 多文件上传样例
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DemoMultipleFilesVO updateDemoMultipleFiles(DemoMultipleFilesVO vo){
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        DemoMultipleFilesEntity entity = ConvertUtils.convertTo(vo, DemoMultipleFilesEntity::new);
        // 3. 执行更新
        int rows = demoMultipleFilesMapper.updateDemoMultipleFiles(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        DemoMultipleFilesEntity updatedEntity = demoMultipleFilesMapper.selectDemoMultipleFilesById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }

        sysAttachmentApi.saveBatch(vo.getId(), "img"+ DemoMultipleFilesVO.class.getName(), vo.getImages());
        sysAttachmentApi.saveBatch(vo.getId(), "file"+ DemoMultipleFilesVO.class.getName(), vo.getAttachments());
        return ConvertUtils.convertTo(updatedEntity, DemoMultipleFilesVO::new);
    }

    /**
     * 批量修改多文件上传样例
     *
     * @param list 多文件上传样例集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateDemoMultipleFiles(List<DemoMultipleFilesVO> list){
        List<DemoMultipleFilesEntity> entityList = ConvertUtils.convertListTo(list, DemoMultipleFilesEntity::new);
        return batchUtils.executeBatch(DemoMultipleFilesMapper.class, entityList, DemoMultipleFilesMapper::updateDemoMultipleFiles);
    }

    /**
     * 删除多文件上传样例
     *
     * @param id 多文件上传样例主键
     * @return 结果
     */
    @Override
    public int deleteDemoMultipleFilesById(Long id){
        return demoMultipleFilesMapper.deleteDemoMultipleFilesById(id);
    }

    /**
     * 批量删除多文件上传样例
     *
     * @param idList 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteDemoMultipleFilesByIdList(List<Long> idList){
        return batchUtils.executeBatch(DemoMultipleFilesMapper.class, idList, DemoMultipleFilesMapper::deleteDemoMultipleFilesById);
    }
}
