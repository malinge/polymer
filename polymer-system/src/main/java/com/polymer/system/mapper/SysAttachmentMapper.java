package com.polymer.system.mapper;

import com.polymer.system.entity.SysAttachmentEntity;
import com.polymer.system.query.SysAttachmentQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 附件管理
 *
 * @author polymer
 */
@Mapper
public interface SysAttachmentMapper {

    /**
     * 查询列表
     *
     * @param query 附件查询
     * @return 附件集合
     */
    List<SysAttachmentEntity> selectSysAttachmentList(SysAttachmentQuery query);

    /**
     * 新增附件表
     *
     * @param sysAttachment 附件
     * @return 结果
     */
    int insertSysAttachment(SysAttachmentEntity sysAttachment);

    /**
     * 删除附件表
     *
     * @param id 需要删除的数据主键
     * @return 结果
     */
    int deleteSysAttachmentById(Long id);

    /**
     * 修改附件
     *
     * @param sysAttachment 附件
     * @return 结果
     */
    int updateSysAttachment(SysAttachmentEntity sysAttachment);

    /**
     * 查询附件集合
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    List<SysAttachmentEntity> selectSysAttachmentByBizMark(@Param("bizMark") String bizMark, @Param("bizType") String bizType);

    /**
     * 批量条件删除附件
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    int delSysAttachmentByBizMark(@Param("bizMark") String bizMark, @Param("bizType") String bizType);
}