package com.polymer.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.message.entity.MessageSmsLogEntity;
import com.polymer.message.mapper.MessageSmsLogMapper;
import com.polymer.message.query.MessageSmsLogQuery;
import com.polymer.message.service.MessageSmsLogService;
import com.polymer.message.vo.MessageSmsLogVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 短信日志
 *
 * @author polymer
 */
@Service
public class MessageSmsLogServiceImpl implements MessageSmsLogService {
    @Resource
    private MessageSmsLogMapper messageSmsLogMapper;

    /**
     * 根据短信日志查询获取分页短信日志
     *
     * @param query 短信日志查询
     * @return 结果
     */
    @Override
    public PageResult<MessageSmsLogVO> page(MessageSmsLogQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<MessageSmsLogEntity> entityList = messageSmsLogMapper.selectMessageSmsLogList(query);
        PageInfo<MessageSmsLogEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, MessageSmsLogVO::new), pageInfo.getTotal());
    }

    /**
     * 根据短信日志主键查询短信日志信息
     *
     * @param id 短信日志主键
     * @return 结果
     */
    @Override
    public MessageSmsLogVO getById(Long id) {
        MessageSmsLogEntity entity = messageSmsLogMapper.selectMessageSmsLogById(id);
        return ConvertUtils.convertTo(entity, MessageSmsLogVO::new);
    }

    /**
     * 保存短信日志信息
     *
     * @param vo 短信日志信息
     * @return 结果
     */
    @Override
    public int save(MessageSmsLogVO vo) {
        return messageSmsLogMapper.insertMessageSmsLog(ConvertUtils.convertTo(vo, MessageSmsLogEntity::new));
    }

}