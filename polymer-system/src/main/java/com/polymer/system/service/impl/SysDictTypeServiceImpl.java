package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.SysDict;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysDictDataEntity;
import com.polymer.system.entity.SysDictTypeEntity;
import com.polymer.system.enums.DictSourceEnum;
import com.polymer.system.mapper.SysDictDataMapper;
import com.polymer.system.mapper.SysDictTypeMapper;
import com.polymer.system.query.SysDictTypeQuery;
import com.polymer.system.service.SysDictTypeService;
import com.polymer.system.vo.SysDictSimpleVO;
import com.polymer.system.vo.SysDictTypeVO;
import com.polymer.system.vo.SysDictVO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典类型
 *
 * @author polymer
 */

@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {
    private static final Logger log = LoggerFactory.getLogger(SysDictTypeServiceImpl.class);
    @Resource
    private SysDictTypeMapper sysDictTypeMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;
    @Resource
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        refreshTransCache();
    }

    /**
     * 根据字典类型查询获取字典类型分页数据
     *
     * @param query 字典类型查询
     * @return PageResult<SysDictTypeVO>
     */
    @Override
    public PageResult<SysDictTypeVO> page(SysDictTypeQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysDictTypeEntity> entityList = sysDictTypeMapper.selectSysDictTypeList(query);
        PageInfo<SysDictTypeEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysDictTypeVO::new), pageInfo.getTotal());
    }

    /**
     * 根据字典类型主键查询字典类型
     *
     * @param id 字典类型主键
     * @return SysDictTypeVO
     */
    @Override
    public SysDictTypeVO getById(Long id) {
        SysDictTypeEntity entity = sysDictTypeMapper.selectSysDictTypeById(id);
        return ConvertUtils.convertTo(entity, SysDictTypeVO::new);
    }

    /**
     * 保存字典类型
     *
     * @param vo 字典类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictTypeVO save(SysDictTypeVO vo) {
        // 1. VO 转 Entity
        SysDictTypeEntity entity = ConvertUtils.convertTo(vo, SysDictTypeEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysDictTypeMapper.insertSysDictType(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysDictTypeVO::new);
    }

    /**
     * 更新字典类型
     *
     * @param vo 字典类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictTypeVO update(SysDictTypeVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        SysDictTypeEntity entity = ConvertUtils.convertTo(vo, SysDictTypeEntity::new);
        // 3. 执行更新
        int rows = sysDictTypeMapper.updateSysDictType(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysDictTypeEntity updatedEntity = sysDictTypeMapper.selectSysDictTypeById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysDictTypeVO::new);
    }

    /**
     * 根据字典类型主键集合删除字典类型
     *
     * @param idList 字典类型主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        batchUtils.executeBatch(SysDictTypeMapper.class, idList, SysDictTypeMapper::deleteSysDictTypeById);
    }

    /**
     * 获取动态SQL数据
     *
     * @param id 字典类型主键
     * @return List<SysDictVO.DictData>
     */
    @Override
    public List<SysDictVO.DictData> getDictSql(Long id) {
        SysDictTypeVO vo = this.getById(id);
        try {
            return sysDictDataMapper.getListForSql(vo.getDictSql());
        } catch (Exception e) {
            throw new ServiceException("动态SQL执行失败，请检查SQL是否正确！");
        }
    }

    /**
     * 获取全部字典列表
     *
     * @return List<SysDictVO>
     */
    @Override
    public List<SysDictVO> getDictList() {
        // 全部字典类型列表
        List<SysDictTypeEntity> typeList = sysDictTypeMapper.selectSysDictTypeList(null);

        // 全部字典数据列表
        List<SysDictDataEntity> dataList = sysDictDataMapper.selectSysDictDataList(null);

        // 按dictTypeId分组,优化查找性能
        Map<Long, List<SysDictDataEntity>> dataMap = dataList.stream()
                .collect(Collectors.groupingBy(SysDictDataEntity::getDictTypeId));

        // 全部字典列表
        List<SysDictVO> dictList = new ArrayList<>(typeList.size());
        for (SysDictTypeEntity type : typeList) {
            SysDictVO dict = new SysDictVO();
            dict.setDictType(type.getDictType());

            // 使用Map直接获取对应的数据,避免嵌套循环
            List<SysDictDataEntity> typeDataList = dataMap.getOrDefault(type.getId(), Collections.emptyList());
            for (SysDictDataEntity data : typeDataList) {
                dict.getDataList().add(new SysDictVO.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass()));
            }

            // 数据来源动态SQL
            if (type.getDictSource() == DictSourceEnum.SQL.getValue()) {
                // 增加动态列表
                String sql = type.getDictSql();
                try {
                    dict.setDataList(sysDictDataMapper.getListForSql(sql));
                } catch (Exception e) {
                    log.error("增加动态字典异常: type={}", type, e);
                }
            }
            dictList.add(dict);
        }
        return dictList;
    }

    /**
     * 刷新字典缓存
     */
    @Override
    public void refreshTransCache() {
        this.delList();
        // 查询列表
        List<SysDict> list = getDictDataList();
        // 保存到缓存
        this.saveList(list);
    }

    private void saveList(List<SysDict> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (SysDict sysDictVO : list) {
            String configKey = CacheConstants.SYS_DICT_KEY + sysDictVO.getDictType();
            redisCache.hSet(CacheConstants.SYS_DICT_CACHE, configKey, sysDictVO.getDataList(), RedisCache.NOT_EXPIRE);
        }
    }

    private void delList() {
        redisCache.delete(CacheConstants.SYS_DICT_CACHE);
    }

    private List<SysDict> getDictDataList() {
        // 全部字典类型列表
        List<SysDictTypeEntity> typeList = sysDictTypeMapper.selectSysDictTypeList(null);

        // 全部字典数据列表
        List<SysDictDataEntity> dataList = sysDictDataMapper.selectSysDictDataList(null);

        // 按dictTypeId分组,优化查找性能
        Map<Long, List<SysDictDataEntity>> dataMap = dataList.stream()
                .collect(Collectors.groupingBy(SysDictDataEntity::getDictTypeId));

        // 全部字典列表
        List<SysDict> dictList = new ArrayList<>(typeList.size());
        for (SysDictTypeEntity type : typeList) {
            SysDict dict = new SysDict();
            dict.setDictType(type.getDictType());

            // 使用Map直接获取对应的数据,避免嵌套循环
            List<SysDictDataEntity> typeDataList = dataMap.getOrDefault(type.getId(), Collections.emptyList());
            for (SysDictDataEntity data : typeDataList) {
                dict.getDataList().add(new SysDict.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass()));
            }
            dictList.add(dict);
        }

        return dictList;
    }

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    @Override
    public List<SysDictSimpleVO> getSimpleDictTypeList() {
        // 全部字典类型列表
        List<SysDictTypeEntity> typeList = sysDictTypeMapper.selectSysDictTypeList(null);

        return ConvertUtils.convertListTo(typeList, SysDictSimpleVO::new);
    }
}
