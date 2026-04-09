package com.polymer.generator.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.common.utils.DbUtils;
import com.polymer.generator.common.utils.GenUtils;
import com.polymer.generator.config.GenDataSource;
import com.polymer.generator.entity.DatasourceEntity;
import com.polymer.generator.entity.TableEntity;
import com.polymer.generator.service.DataSourceService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 数据源管理
 * polymer@126.com
 *
 */
@RestController
@RequestMapping("generator/gen/datasource")
public class DataSourceController {
    @Resource
    private DataSourceService datasourceService;

    @GetMapping("page")
    public Result<PageResult<DatasourceEntity>> page(Query query) {
        PageResult<DatasourceEntity> page = datasourceService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    public Result<List<DatasourceEntity>> list() {
        List<DatasourceEntity> list = datasourceService.getList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    public Result<DatasourceEntity> get(@PathVariable("id") Long id) {
        DatasourceEntity data = datasourceService.selectDatasourceById(id);

        return Result.ok(data);
    }

    @GetMapping("test/{id}")
    public Result<String> test(@PathVariable("id") Long id) {
        try {
            DatasourceEntity entity = datasourceService.selectDatasourceById(id);

            DbUtils.getConnection(new GenDataSource(entity));
            return Result.ok("连接成功");
        } catch (Exception e) {
            return Result.error("连接失败，请检查配置信息");
        }
    }

    @PostMapping
    public Result<DatasourceEntity> save(@RequestBody DatasourceEntity entity) {
        DatasourceEntity resEntity = datasourceService.save(entity);

        return Result.ok(resEntity);
    }

    @PutMapping
    public Result<DatasourceEntity> update(@RequestBody DatasourceEntity entity) {
        DatasourceEntity resEntity = datasourceService.updateById(entity);

        return Result.ok(resEntity);
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        datasourceService.removeBatchByIds(Arrays.asList(ids));

        return Result.ok();
    }

    /**
     * 根据数据源ID，获取全部数据表
     *
     * @param id 数据源ID
     */
    @GetMapping("table/list/{id}")
    public Result<List<TableEntity>> tableList(@PathVariable("id") Long id) {
        try {
            // 获取数据源
            GenDataSource datasource = datasourceService.get(id);
            // 根据数据源，获取全部数据表
            List<TableEntity> tableList = GenUtils.getTableList(datasource);

            return Result.ok(tableList);
        } catch (Exception e) {
            return Result.error("数据源配置错误，请检查数据源配置！");
        }
    }
}