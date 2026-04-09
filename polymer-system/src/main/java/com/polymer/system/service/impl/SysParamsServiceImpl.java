package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysParamsEntity;
import com.polymer.system.mapper.SysParamsMapper;
import com.polymer.system.query.SysParamsQuery;
import com.polymer.system.service.SysParamsService;
import com.polymer.system.vo.SysParamsVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数管理
 *
 * @author polymer
 */
@Service
public class SysParamsServiceImpl implements SysParamsService {
    @Resource
    private SysParamsMapper sysParamsMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据参数管理查询获取分页参数列表
     *
     * @param query 参数管理查询
     * @return PageResult<SysParamsVO>
     */
    @Override
    public PageResult<SysParamsVO> page(SysParamsQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysParamsEntity> entityList = sysParamsMapper.selectSysParamsList(query);
        PageInfo<SysParamsEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysParamsVO::new), pageInfo.getTotal());
    }

    /**
     * 保存参数信息
     *
     * @param vo 参数信息
     */
    @Override
    @CachePut(value = CacheConstants.SYSTEM_PARAMS_KEY, key = "#result.paramKey")
    public SysParamsVO save(SysParamsVO vo) {
        // 判断 参数键 是否存在
        boolean exist = sysParamsMapper.isExist(vo.getParamKey());
        if (exist) {
            throw new ServiceException("参数键已存在");
        }
        SysParamsEntity entity = ConvertUtils.convertTo(vo, SysParamsEntity::new);
        sysParamsMapper.insertSysParams(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysParamsVO::new);
    }

    /**
     * 更新参数信息
     *
     * @param vo 参数信息
     */
    @Override
    @CachePut(value = CacheConstants.SYSTEM_PARAMS_KEY, key = "#result.paramKey")
    public SysParamsVO update(SysParamsVO vo) {
        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }

        SysParamsEntity entity = sysParamsMapper.selectSysParamsById(vo.getId());

        // 如果 参数键 修改过
        if (!StringUtils.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
            // 判断 新参数键 是否存在
            boolean exist = sysParamsMapper.isExist(vo.getParamKey());
            if (exist) {
                throw new ServiceException("参数键已存在");
            }

        }
        // 修改数据
        int rows = sysParamsMapper.updateSysParams(ConvertUtils.convertTo(vo, SysParamsEntity::new));
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysParamsEntity updatedEntity = sysParamsMapper.selectSysParamsById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysParamsVO::new);
    }

    /**
     * 根据参数信息主键集合删除参数信息
     *
     * @param idList 参数信息主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.SYSTEM_PARAMS_KEY, allEntries = true)
    public void delete(List<Long> idList) {
        // 删除数据
        batchUtils.executeBatch(SysParamsMapper.class, idList, SysParamsMapper::deleteSysParamsById);
    }

    /**
     * 根据paramKey，获取字符串值
     *
     * @param paramKey 参数Key
     * @return SysParamsVO
     */
    @Override
    @Cacheable(value = CacheConstants.SYSTEM_PARAMS_KEY, key = "#paramKey")
    public SysParamsVO getParamsByparamKey(String paramKey) {
        SysParamsEntity entity = sysParamsMapper.get(paramKey);
        if (entity == null) {
            throw new ServiceException("参数值不存在，paramKey：" + paramKey);
        }

        return ConvertUtils.convertTo(entity, SysParamsVO::new);
    }

    /**
     * 根据参数主键查询参数信息
     *
     * @param id 参数主键
     * @return SysParamsVO
     */
    @Override
    public SysParamsVO getById(Long id) {
        SysParamsEntity entity = sysParamsMapper.selectSysParamsById(id);

        return ConvertUtils.convertTo(entity, SysParamsVO::new);
    }

}