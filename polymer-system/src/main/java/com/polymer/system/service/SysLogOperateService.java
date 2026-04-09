package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.logger.event.OperateLogEvent;
import com.polymer.system.query.SysLogOperateQuery;
import com.polymer.system.vo.SysLogOperateVO;

/**
 * 操作日志
 *
 * @author polymer
 */
public interface SysLogOperateService {

    /**
     * 根据操作日志查询获取分页操作日志列表
     *
     * @param query 操作日志查询
     * @return PageResult<SysLogOperateVO>
     */
    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);

    void asyncOperateProcess(OperateLogEvent event);
}