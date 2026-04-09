package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.ExcelUtil;
import com.polymer.framework.common.utils.HttpContextUtils;
import com.polymer.framework.common.utils.IpUtils;
import com.polymer.system.entity.SysLogLoginEntity;
import com.polymer.system.mapper.SysLogLoginMapper;
import com.polymer.system.query.SysLogLoginQuery;
import com.polymer.system.service.SysLogLoginService;
import com.polymer.system.vo.SysLogLoginVO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录日志
 *
 * @author polymer
 */
@Service
public class SysLogLoginServiceImpl implements SysLogLoginService {
    @Resource
    private SysLogLoginMapper sysLogLoginMapper;

    /**
     * 根据登录日志查询获取分页登录日志列表
     *
     * @param query 登录日志查询
     * @return PageResult<SysLogLoginVO>
     */
    @Override
    public PageResult<SysLogLoginVO> page(SysLogLoginQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysLogLoginEntity> entityList = sysLogLoginMapper.selectSysLogLoginList(query);
        PageInfo<SysLogLoginEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysLogLoginVO::new), pageInfo.getTotal());
    }

    /**
     * 保存登录日志
     *
     * @param username  用户名
     * @param status    登录状态
     * @param operation 操作信息
     * @return 保存数量
     */
    @Override
    public int saveLogLogin(String username, Integer status, Integer operation) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        SysLogLoginEntity entity = new SysLogLoginEntity();
        entity.setUsername(username);
        entity.setStatus(status);
        entity.setOperation(operation);
        if (request != null) {
            String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
            String ip = IpUtils.getIpAddr(request);
            String address = IpUtils.getAddressByIP(ip);
            entity.setIp(ip);
            entity.setAddress(address);
            entity.setUserAgent(userAgent);
        }
        return sysLogLoginMapper.insertLogLogin(entity);
    }

    /**
     * 导出登录日志
     *
     * @return byte[]
     */
    @Override
    public byte[] export() {
        List<SysLogLoginEntity> list = sysLogLoginMapper.selectSysLogLoginList(null);
        List<SysLogLoginVO> sysLogLoginVOS = ConvertUtils.convertListTo(list, SysLogLoginVO::new);
        ExcelUtil<SysLogLoginVO> util = new ExcelUtil<>(SysLogLoginVO.class);
        return util.exportExcel(sysLogLoginVOS, "登录日志");
    }

}