package com.polymer.system.api;

import com.polymer.api.system.SysAttachmentApi;
import com.polymer.api.system.dto.SysAttachmentDTO;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.system.entity.SysAttachmentEntity;
import com.polymer.system.service.SysAttachmentService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * BelongsProject: polymer-system
 * BelongsPackage: net.polymer.system.api
 * CreateTime: 2023-11-08  14:36
 * Description: 附件实现
 *
 * @author polymer
 * @version 2.0
 */
@Component
public class SysAttachmentApiImpl implements SysAttachmentApi {
    @Resource
    private SysAttachmentService sysAttachmentService;

    /**
     * 批量保存附件集合
     *
     * @param attachmentDTOList 附件集合
     */
    @Override
    public void saveBatch(List<SysAttachmentDTO> attachmentDTOList) {
        sysAttachmentService.batchSysAttachment(ConvertUtils.convertListTo(attachmentDTOList, SysAttachmentEntity::new));
    }

    /**
     * 根据业务标识和业务类型查询附件集合
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    @Override
    public List<SysAttachmentDTO> findListByBizMark(String bizMark, String bizType) {
        List<SysAttachmentEntity> infoListByBizMark = sysAttachmentService.findListByBizMark(bizMark, bizType);
        return ConvertUtils.convertListTo(infoListByBizMark, SysAttachmentDTO::new);
    }

    /**
     * 根据业务标识和业务类型删除附件
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     */
    @Override
    public void delByBizMark(String bizMark, String bizType) {
        sysAttachmentService.delByBizMark(bizMark, bizType);
    }
}
