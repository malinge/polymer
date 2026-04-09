package ${package}.${moduleName}.service.impl;

import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import ${package}.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import ${package}.${moduleName}.entity.${ClassName}Entity;
import ${package}.${moduleName}.vo.${ClassName}VO;
import ${package}.${moduleName}.query.${ClassName}Query;
import ${package}.${moduleName}.mapper.${ClassName}Mapper;
import ${package}.${moduleName}.service.${ClassName}Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${tableComment}Service业务层处理
 *
 * @author ${author} ${email}
 * @since ${version} ${date}
 */
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service {
    @Resource
    private ${ClassName}Mapper ${className}Mapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 查询${tableComment}分页列表
     *
     * @param query 查询条件
     * @return ${tableComment}分页集合
     */
    @Override
    public PageResult<${ClassName}VO> page(${ClassName}Query query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<${ClassName}Entity> entityList = ${className}Mapper.select${ClassName}List(query);
        PageInfo<${ClassName}Entity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, ${ClassName}VO::new), pageInfo.getTotal());
    }

    /**
     * 查询${tableComment}
     *
     * @param ${primary.attrName} ${tableComment}主键
     * @return ${tableComment}
     */
    @Override
    public ${ClassName}VO select${ClassName}By${primary.attrName?cap_first}(${primary.attrType} ${primary.attrName}){
        ${ClassName}Entity entity =  ${className}Mapper.select${ClassName}By${primary.attrName?cap_first}(${primary.attrName});
        return ConvertUtils.convertTo(entity, ${ClassName}VO::new);
    }

    /**
     * 新增${tableComment}
     *
     * @param vo ${tableComment}
     * @return 结果
     */
    @Override
    public ${ClassName}VO insert${ClassName}(${ClassName}VO vo){
        // 1. VO 转 Entity
        ${ClassName}Entity entity = ConvertUtils.convertTo(vo, ${ClassName}Entity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        ${className}Mapper.insert${ClassName}(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, ${ClassName}VO::new);
    }

    /**
     * 批量新增${tableComment}
     *
     * @param list ${tableComment}集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsert${ClassName}(List<${ClassName}VO> list){
        List<${ClassName}Entity> entityList = ConvertUtils.convertListTo(list, ${ClassName}Entity::new);
        return batchUtils.executeBatch(${ClassName}Mapper.class, entityList, ${ClassName}Mapper::insert${ClassName});
    }

    /**
     * 修改${tableComment}
     *
     * @param vo ${tableComment}
     * @return 结果
     */
    @Override
    public ${ClassName}VO update${ClassName}(${ClassName}VO vo){
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.get${primary.attrName?cap_first}() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        ${ClassName}Entity entity = ConvertUtils.convertTo(vo, ${ClassName}Entity::new);
        // 3. 执行更新
        int rows = ${className}Mapper.update${ClassName}(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        ${ClassName}Entity updatedEntity = ${className}Mapper.select${ClassName}By${primary.attrName?cap_first}(vo.get${primary.attrName?cap_first}());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, ${ClassName}VO::new);
    }

    /**
     * 批量修改${tableComment}
     *
     * @param list ${tableComment}集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdate${ClassName}(List<${ClassName}VO> list){
        List<${ClassName}Entity> entityList = ConvertUtils.convertListTo(list, ${ClassName}Entity::new);
        return batchUtils.executeBatch(${ClassName}Mapper.class, entityList, ${ClassName}Mapper::update${ClassName});
    }

    /**
     * 删除${tableComment}
     *
     * @param ${primary.attrName} ${tableComment}主键
     * @return 结果
     */
    @Override
    public int delete${ClassName}By${primary.attrName?cap_first}(${primary.attrType} ${primary.attrName}){
        return ${className}Mapper.delete${ClassName}By${primary.attrName?cap_first}(${primary.attrName});
    }

    /**
     * 批量删除${tableComment}
     *
     * @param ${primary.attrName}List 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int delete${ClassName}By${primary.attrName?cap_first}List(List<${primary.attrType}> ${primary.attrName}List){
        return batchUtils.executeBatch(${ClassName}Mapper.class, ${primary.attrName}List, ${ClassName}Mapper::delete${ClassName}By${primary.attrName?cap_first});
    }
}