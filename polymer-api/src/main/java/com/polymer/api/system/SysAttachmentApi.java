package com.polymer.api.system;

import com.polymer.api.system.dto.SysAttachmentDTO;

import java.util.List;

/**
 * BelongsProject: polymer-api
 * BelongsPackage: net.polymer.api.system.system
 * CreateTime: 2023-11-08  14:18
 * Description: 附件api
 *
 * @author polymer
 * @version 2.0
 */
public interface SysAttachmentApi {

    /**
     * 批量保存附件集合
     *
     * @param attachmentDTOList 附件集合
     */
    void saveBatch(List<SysAttachmentDTO> attachmentDTOList);

    /**
     * 根据业务标识和业务类型删除附件
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     */
    void delByBizMark(String bizMark, String bizType);

    /**
     * 根据业务标识和业务类型查询附件集合
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    List<SysAttachmentDTO> findListByBizMark(String bizMark, String bizType);
}
