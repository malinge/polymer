package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.logger.event.OperateLogEvent;
import com.polymer.system.entity.SysLogOperateEntity;
import com.polymer.system.mapper.SysLogOperateMapper;
import com.polymer.system.query.SysLogOperateQuery;
import com.polymer.system.service.SysLogOperateService;
import com.polymer.system.vo.SysLogOperateVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志
 *
 * @author polymer
 */

@Service
public class SysLogOperateServiceImpl implements SysLogOperateService {
    @Resource
    private SysLogOperateMapper sysLogOperateMapper;

    /**
     * 根据操作日志查询获取分页操作日志列表
     *
     * @param query 操作日志查询
     * @return PageResult<SysLogOperateVO>
     */
    @Override
    public PageResult<SysLogOperateVO> page(SysLogOperateQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysLogOperateEntity> entityList = sysLogOperateMapper.selectSysLogOperateList(query);
        PageInfo<SysLogOperateEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysLogOperateVO::new), pageInfo.getTotal());
    }

    @Override
    @Async("logExecutor")
    public void asyncOperateProcess(OperateLogEvent event) {
        SysLogOperateEntity entity = ConvertUtils.convertTo(event, SysLogOperateEntity::new);
        sysLogOperateMapper.insertSysLogOperate(entity);
    }
}