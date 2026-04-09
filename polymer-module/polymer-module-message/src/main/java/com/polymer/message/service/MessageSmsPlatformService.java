package com.polymer.message.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.message.query.MessageSmsPlatformQuery;
import com.polymer.message.sms.config.SmsConfig;
import com.polymer.message.vo.MessageSmsPlatformVO;

import java.util.List;

/**
 * 短信平台
 *
 * @author polymer
 */
public interface MessageSmsPlatformService {

    /**
     * 根据短信平台查询获取分页短信平台
     *
     * @param query 短信平台查询
     * @return 结果
     */
    PageResult<MessageSmsPlatformVO> page(MessageSmsPlatformQuery query);

    /**
     * 启用的短信平台列表
     *
     * @return 结果
     */
    List<SmsConfig> listByEnable();

    /**
     * 保存短信平台信息
     *
     * @param vo 短信平台信息
     * @return 保存个数
     */
    MessageSmsPlatformVO save(MessageSmsPlatformVO vo);

    /**
     * 更新短信平台信息
     *
     * @param vo 短信平台信息
     * @return 更新个数
     */
    MessageSmsPlatformVO update(MessageSmsPlatformVO vo);

    /**
     * 根据短信平台主键删除短信平台信息
     *
     * @param idList 短信平台主键
     * @return 更新个数
     */
    int delete(List<Long> idList);

    /**
     * 根据短信平台主键查询短信平台信息
     *
     * @param id 短信平台主键
     * @return 短信平台信息
     */
    MessageSmsPlatformVO getById(Long id);
}