package com.polymer.message.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.message.query.MessageSmsLogQuery;
import com.polymer.message.vo.MessageSmsLogVO;

/**
 * 短信日志
 *
 * @author polymer
 */
public interface MessageSmsLogService {

    /**
     * 根据短信日志查询获取分页短信日志
     *
     * @param query 短信日志查询
     * @return 结果
     */
    PageResult<MessageSmsLogVO> page(MessageSmsLogQuery query);

    /**
     * 根据短信日志主键查询短信日志信息
     *
     * @param id 短信日志主键
     * @return 结果
     */
    MessageSmsLogVO getById(Long id);

    /**
     * 保存短信日志信息
     *
     * @param logVO 短信日志信息
     * @return 结果
     */
    int save(MessageSmsLogVO logVO);
}