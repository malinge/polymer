package com.polymer.gen.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.gen.common.query.Query;
import com.polymer.gen.config.DbType;
import com.polymer.gen.config.GenDataSource;
import com.polymer.gen.entity.DatasourceEntity;
import com.polymer.gen.mapper.DatasourceMapper;
import com.polymer.gen.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 数据源管理
 * polymer@126.com
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {
    private static final Logger log = LoggerFactory.getLogger(DataSourceServiceImpl.class);
    @Resource
    private DatasourceMapper datasourceMapper;
    @Resource
    private DataSource dataSource;

    @Override
    public PageResult<DatasourceEntity> page(Query query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DatasourceEntity> entityList = datasourceMapper.selectDatasourceList(query);
        PageInfo<DatasourceEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(entityList, pageInfo.getTotal());
    }

    @Override
    public List<DatasourceEntity> getList() {
        return datasourceMapper.selectDatasourceList(null);
    }

    @Override
    public String getDatabaseProductName(Long dataSourceId) {
        if (dataSourceId.intValue() == 0) {
            return DbType.MySQL.name();
        } else {
            DatasourceEntity datasourceEntity = datasourceMapper.selectDatasourceById(dataSourceId);
            if(datasourceEntity != null){
                return datasourceMapper.selectDatasourceById(dataSourceId).getDbType();
            }else {
                throw new ServiceException("数据源不存在！");
            }

        }
    }

    @Override
    public GenDataSource get(Long datasourceId) {
        // 初始化配置信息
        GenDataSource info = null;
        if (datasourceId.intValue() == 0) {
            try {
                info = new GenDataSource(dataSource.getConnection());
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            info = new GenDataSource(datasourceMapper.selectDatasourceById(datasourceId));
        }
        return info;
    }

    @Override
    public DatasourceEntity save(DatasourceEntity entity) {
        entity.setCreateTime(LocalDateTime.now());
        datasourceMapper.insertDatasource(entity);
        return entity;
    }

    @Override
    public DatasourceEntity selectDatasourceById(Long id) {
        return datasourceMapper.selectDatasourceById(id);
    }

    @Override
    public DatasourceEntity updateById(DatasourceEntity entity) {
        datasourceMapper.updateDatasource(entity);

        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (entity.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 执行更新
        int rows = datasourceMapper.updateDatasource(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        DatasourceEntity updatedEntity = datasourceMapper.selectDatasourceById(entity.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return updatedEntity;
    }

    @Override
    public void removeBatchByIds(List<Long> list) {
        for(Long id: list){
            datasourceMapper.deleteDatasourceById(id);
        }
    }
}