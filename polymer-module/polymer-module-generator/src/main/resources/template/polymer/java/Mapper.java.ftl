package ${package}.${moduleName}.mapper;

import java.util.List;
import ${package}.${moduleName}.entity.${ClassName}Entity;
import ${package}.${moduleName}.query.${ClassName}Query;
import org.apache.ibatis.annotations.Mapper;

/**
* ${tableComment}Mapper接口
*
* @author ${author} ${email}
* @since ${version} ${date}
*/
@Mapper
public interface ${ClassName}Mapper {
    
    /**
     * 查询${tableComment}列表
     * 
     * @param query ${tableComment}查询
     * @return ${tableComment}集合
     */
    List<${ClassName}Entity> select${ClassName}List(${ClassName}Query query);

    /**
     * 查询${tableComment}
     *
     * @param ${primary.attrName} ${tableComment}主键
     * @return ${tableComment}
     */
    ${ClassName}Entity select${ClassName}By${primary.attrName?cap_first}(${primary.attrType} ${primary.attrName});

    /**
     * 新增${tableComment}
     * 
     * @param ${className} ${tableComment}
     * @return 结果
     */
    int insert${ClassName}(${ClassName}Entity ${className});

    /**
     * 修改${tableComment}
     * 
     * @param ${className} ${tableComment}
     * @return 结果
     */
    int update${ClassName}(${ClassName}Entity ${className});

    /**
     * 删除${tableComment}
     * 
     * @param ${primary.attrName} ${tableComment}主键
     * @return 结果
     */
    int delete${ClassName}By${primary.attrName?cap_first}(${primary.attrType} ${primary.attrName});

}