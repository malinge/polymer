package ${package}.${moduleName}.service;

import ${package}.framework.common.pojo.PageResult;
import ${package}.${moduleName}.vo.${ClassName}VO;
import ${package}.${moduleName}.query.${ClassName}Query;

import java.util.List;

/**
 * ${tableComment}Service接口
 *
 * @author ${author} ${email}
 * @since ${version} ${date}
 */
public interface ${ClassName}Service {

     /**
     * 查询${tableComment}分页列表
     *
     * @param query 查询条件
     * @return ${tableComment}分页集合
     */
    PageResult<${ClassName}VO> page(${ClassName}Query query);

    /**
     * 查询${tableComment}
     *
     * @param ${primary.attrName} ${tableComment}主键
     * @return ${tableComment}
     */
    ${ClassName}VO select${ClassName}By${primary.attrName?cap_first}(${primary.attrType} ${primary.attrName});

    /**
     * 新增${tableComment}
     *
     * @param vo ${tableComment}
     * @return 结果
     */
    ${ClassName}VO insert${ClassName}(${ClassName}VO vo);

    /**
     * 批量新增${tableComment}
     *
     * @param list ${tableComment}集合
     * @return 结果
     */
    int batchInsert${ClassName}(List<${ClassName}VO> list);

    /**
     * 修改${tableComment}
     *
     * @param vo ${tableComment}
     * @return 结果
     */
     ${ClassName}VO update${ClassName}(${ClassName}VO vo);

    /**
     * 批量修改${tableComment}
     *
     * @param list ${tableComment}集合
     * @return 结果
     */
    int batchUpdate${ClassName}(List<${ClassName}VO> list);

    /**
     * 删除${tableComment}
     *
     * @param ${primary.attrName} ${tableComment}主键
     * @return 结果
     */
    int delete${ClassName}By${primary.attrName?cap_first}(${primary.attrType} ${primary.attrName});

    /**
     * 批量删除${tableComment}
     *
     * @param ${primary.attrName}List 需要删除的数据主键集合
     * @return 结果
     */
    int delete${ClassName}By${primary.attrName?cap_first}List(List<${primary.attrType}> ${primary.attrName}List);

}