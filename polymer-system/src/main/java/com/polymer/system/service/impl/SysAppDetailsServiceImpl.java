package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysAppDetailsEntity;
import com.polymer.system.mapper.SysAppDetailsMapper;
import com.polymer.system.query.SysAppDetailsQuery;
import com.polymer.system.service.SysAppDetailsService;
import com.polymer.system.vo.SysAppDetailsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * app信息表Service业务层处理
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2025-05-09
 */
@Service
public class SysAppDetailsServiceImpl implements SysAppDetailsService {
    @Resource
    private SysAppDetailsMapper sysAppDetailsMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private MyBatisBatchUtils batchUtils;

    @PostConstruct
    public void init() {
        reloadSysAppDetails();
    }

    /**
     * 查询app信息表分页列表
     *
     * @param query 查询条件
     * @return app信息表分页集合
     */
    @Override
    public PageResult<SysAppDetailsVO> page(SysAppDetailsQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysAppDetailsEntity> entityList = sysAppDetailsMapper.selectSysAppDetailsList(query);
        PageInfo<SysAppDetailsEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysAppDetailsVO::new), pageInfo.getTotal());
    }

    /**
     * 查询app信息表
     *
     * @param id app信息表主键
     * @return app信息表
     */
    @Override
    public SysAppDetailsVO selectSysAppDetailsById(Integer id) {
        SysAppDetailsEntity entity = sysAppDetailsMapper.selectSysAppDetailsById(id);
        return ConvertUtils.convertTo(entity, SysAppDetailsVO::new);
    }

    /**
     * 新增app信息表
     *
     * @param vo app信息表
     * @return 结果
     */
    @Override
    public SysAppDetailsVO insertSysAppDetails(SysAppDetailsVO vo) {
        // 1. VO 转 Entity
        SysAppDetailsEntity entity = ConvertUtils.convertTo(vo, SysAppDetailsEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysAppDetailsMapper.insertSysAppDetails(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysAppDetailsVO::new);
    }

    /**
     * 批量新增app信息表
     *
     * @param list app信息表集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsertSysAppDetails(List<SysAppDetailsVO> list) {
        List<SysAppDetailsEntity> entityList = ConvertUtils.convertListTo(list, SysAppDetailsEntity::new);
        return batchUtils.executeBatch(SysAppDetailsMapper.class, entityList, SysAppDetailsMapper::insertSysAppDetails);
    }

    /**
     * 修改app信息表
     *
     * @param vo app信息表
     * @return 结果
     */
    @Override
    public SysAppDetailsVO updateSysAppDetails(SysAppDetailsVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        SysAppDetailsEntity entity = ConvertUtils.convertTo(vo, SysAppDetailsEntity::new);
        // 3. 执行更新
        int rows = sysAppDetailsMapper.updateSysAppDetails(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysAppDetailsEntity updatedEntity = sysAppDetailsMapper.selectSysAppDetailsById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysAppDetailsVO::new);
    }

    /**
     * 批量修改app信息表
     *
     * @param list app信息表集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateSysAppDetails(List<SysAppDetailsVO> list) {
        List<SysAppDetailsEntity> entityList = ConvertUtils.convertListTo(list, SysAppDetailsEntity::new);
        return batchUtils.executeBatch(SysAppDetailsMapper.class, entityList, SysAppDetailsMapper::updateSysAppDetails);
    }

    @Override
    public void reloadSysAppDetails() {
        redisCache.deleteKeysByPatternScan(CacheConstants.SYS_APPID_KEY + "*");
        List<SysAppDetailsEntity> entityList = sysAppDetailsMapper.selectSysAppDetailsList(null);
        for (SysAppDetailsEntity entity : entityList) {
            String key = CacheConstants.SYS_APPID_KEY +entity.getAppId();
            redisCache.set(key, entity.getAppSecret());
        }
    }

}