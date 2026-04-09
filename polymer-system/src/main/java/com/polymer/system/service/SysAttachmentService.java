package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.entity.SysAttachmentEntity;
import com.polymer.system.query.SysAttachmentQuery;
import com.polymer.system.vo.SysAttachmentVO;

import java.util.List;

/**
 * 附件管理
 *
 * @author polymer
 */
public interface SysAttachmentService {

    /**
     * 根据附件管理查询获取分页附件数据
     *
     * @param query 附件管理查询
     * @return 结果
     */
    PageResult<SysAttachmentVO> selectSysAttachmentList(SysAttachmentQuery query);

    /**
     * 保存附件
     *
     * @param vo 附件
     * @return 保存数量
     */
    SysAttachmentVO insertSysAttachment(SysAttachmentVO vo);

    /**
     * 更新附件
     *
     * @param sysAttachment 附件
     * @return 更新数量
     */
    int updateSysAttachment(SysAttachmentEntity sysAttachment);

    /**
     * 根据附件主键集合删除附件信息
     *
     * @param idList 附件主键集合
     * @return 删除数量
     */
    int deleteSysAttachmentByIds(List<Long> idList);

    /**
     * 批量保存附件集合
     *
     * @param list 附件集合
     * @return 结果
     */
    int batchSysAttachment(List<SysAttachmentEntity> list);

    /**
     * 根据业务标识和业务类型查询附件集合
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    List<SysAttachmentEntity> findListByBizMark(String bizMark, String bizType);

    /**
     * 根据业务标识和业务类型删除附件
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    int delByBizMark(String bizMark, String bizType);
}