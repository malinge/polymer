package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.CollectionUtils;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.ExcelUtil;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.framework.security.core.cache.TokenStoreCache;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.framework.security.core.utils.TokenUtils;
import com.polymer.system.entity.SysUserEntity;
import com.polymer.system.entity.SysUserRoleEntity;
import com.polymer.system.enums.SuperAdminEnum;
import com.polymer.system.mapper.SysUserMapper;
import com.polymer.system.query.SysRoleUserQuery;
import com.polymer.system.query.SysUserQuery;
import com.polymer.system.service.SysDeptService;
import com.polymer.system.service.SysPostService;
import com.polymer.system.service.SysRoleService;
import com.polymer.system.service.SysUserPostService;
import com.polymer.system.service.SysUserRoleService;
import com.polymer.system.service.SysUserService;
import com.polymer.system.service.SysUserTokenService;
import com.polymer.system.vo.SysUserBaseVO;
import com.polymer.system.vo.SysUserExcelVO;
import com.polymer.system.vo.SysUserSelectVO;
import com.polymer.system.vo.SysUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户管理
 *
 * @author polymer
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysUserPostService sysUserPostService;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysPostService sysPostService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private TokenStoreCache tokenStoreCache;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据用户查询获取分页用户列表
     *
     * @param query 用户查询
     * @return PageResult<SysUserVO>
     */
    @Override
    public PageResult<SysUserVO> page(SysUserQuery query) {
        query.setDeptIds(getOrgCondition(query.getDeptId()));
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysUserEntity> entityList = sysUserMapper.selectSysUserList(query);
        PageInfo<SysUserEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysUserVO::new), pageInfo.getTotal());
    }

    /**
     * 获得部门条件：查询指定部门的子部门编号们，包括自身
     *
     * @param deptId 部门编号
     * @return 部门编号集合
     */
    private Set<Long> getOrgCondition(Long deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        List<Long> subDeptIdList = sysDeptService.getDeptTreeIds(deptId);
        return new HashSet<>(subDeptIdList);
    }

    /**
     * 保存用户信息
     *
     * @param vo 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserVO save(SysUserVO vo) {
        // 城市编码
        if(vo.getCity() != null && vo.getCity().size() == 3){
            vo.setProvinceCode(vo.getCity().get(0));
            vo.setCityCode(vo.getCity().get(1));
            vo.setDistrictCode(vo.getCity().get(2));
        }

        SysUserEntity entity = ConvertUtils.convertTo(vo, SysUserEntity::new);
        entity.setSuperAdmin(SuperAdminEnum.NO.getValue());
        // 判断用户名是否存在
        SysUserEntity user = sysUserMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServiceException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = sysUserMapper.selectSysUserByMobile(entity.getMobile());
        if (user != null) {
            throw new ServiceException("手机号已经存在");
        }

        // 保存用户
        sysUserMapper.insertSysUser(entity);

        // 保存用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());

        return ConvertUtils.convertTo(entity, SysUserVO::new);
    }

    /**
     * 更新用户信息
     *
     * @param vo 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserVO update(SysUserVO vo) {
        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 城市编码
        if(vo.getCity() != null && vo.getCity().size() == 3){
            vo.setProvinceCode(vo.getCity().get(0));
            vo.setCityCode(vo.getCity().get(1));
            vo.setDistrictCode(vo.getCity().get(2));
        }

        SysUserEntity entity = ConvertUtils.convertTo(vo, SysUserEntity::new);
        // 判断用户名是否存在
        SysUserEntity user = sysUserMapper.getByUsername(entity.getUsername());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServiceException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = sysUserMapper.selectSysUserByMobile(entity.getMobile());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServiceException("手机号已经存在");
        }

        // 更新用户
        int rows = sysUserMapper.updateSysUser(entity);

        // 更新用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());

        // 更新用户缓存权限
        sysUserTokenService.updateCacheAuthByUserId(entity.getId());

        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysUserEntity updatedEntity = sysUserMapper.selectSysUserById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysUserVO::new);
    }

    /**
     * 更新登录用户信息
     *
     * @param vo 用户信息
     */
    @Override
    public void updateLoginInfo(SysUserBaseVO vo) {
        // 城市编码
        if(vo.getCity() != null && vo.getCity().size() == 3){
            vo.setProvinceCode(vo.getCity().get(0));
            vo.setCityCode(vo.getCity().get(1));
            vo.setDistrictCode(vo.getCity().get(2));
        }

        SysUserEntity entity = ConvertUtils.convertTo(vo, SysUserEntity::new);
        // 设置登录用户ID
        entity.setId(SecurityUser.getUserId());

        // 判断手机号是否存在
        SysUserEntity user = sysUserMapper.selectSysUserByMobile(entity.getMobile());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServiceException("手机号已经存在");
        }

        // 更新用户
        sysUserMapper.updateSysUser(entity);

        // 删除用户缓存
        tokenStoreCache.deleteUser(TokenUtils.getAccessToken());
    }

    /**
     * 根据用户信息主键集合删除用户信息
     *
     * @param idList 用户信息主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 删除用户
        batchUtils.executeBatch(SysUserMapper.class, idList, SysUserMapper::deleteSysUserById);

        // 删除用户角色关系
        sysUserRoleService.deleteByUserIdList(idList);

        // 删除用户岗位关系
        sysUserPostService.deleteByUserIdList(idList);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param mobile 手机号
     * @return SysUserVO
     */
    @Override
    public SysUserVO getByMobile(String mobile) {
        SysUserEntity entity = sysUserMapper.selectSysUserByMobile(mobile);
        return ConvertUtils.convertTo(entity, SysUserVO::new);
    }

    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     */
    @Override
    public void updatePassword(Long id, String newPassword) {
        // 修改密码
        SysUserEntity user = sysUserMapper.selectSysUserById(id);
        user.setPassword(newPassword);

        sysUserMapper.updateSysUser(user);
    }

    /**
     * 分配角色，用户列表
     *
     * @param query 分配角色查询
     * @return PageResult<SysUserVO>
     */
    @Override
    public PageResult<SysUserVO> roleUserPage(SysRoleUserQuery query) {
        query.setDeptIds(getRoleUserCondition(query.getRoleId()));
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysUserEntity> entityList = sysUserMapper.getRoleUserList(query);
        PageInfo<SysUserEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysUserVO::new), pageInfo.getTotal());
    }

    /**
     * 获得角色条件：查询指定角色的用户编号们
     *
     * @param roleId 角色编号
     * @return 用户编号集合
     */
    private Set<Long> getRoleUserCondition(Long roleId) {
        if (roleId == null) {
            return Collections.emptySet();
        }
        return CollectionUtils.convertSet(sysUserRoleService.getUserIdList(roleId), SysUserRoleEntity::getUserId);
    }

    /**
     * 批量导入用户
     *
     * @param file     excel文件
     * @param password 密码
     * @return String
     */
    @Override
    public String importByExcel(MultipartFile file, String password) throws Exception {

        ExcelUtil<SysUserExcelVO> util = new ExcelUtil<>(SysUserExcelVO.class);
        List<SysUserExcelVO> userList = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysUserExcelVO user : userList) {
            try {
                // 验证是否存在这个用户
                // 判断用户名是否存在
                SysUserEntity u = sysUserMapper.getByUsername(user.getUsername());
                if (StringUtils.isNull(u)) {
                    SysUserEntity entity = ConvertUtils.convertTo(user, SysUserEntity::new);
                    sysDeptService.checkDeptDataScope(entity.getDeptId());
                    entity.setPassword(password);
                    entity.setSuperAdmin(SuperAdminEnum.NO.getValue());
                    sysUserMapper.insertSysUser(entity);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUsername()).append(" 导入成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getUsername()).append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUsername() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();

    }

    /**
     * 导出用户信息表格
     *
     * @return byte[]
     */
    @Override
    public byte[] export(SysUserQuery query) {
        List<SysUserEntity> list = sysUserMapper.selectSysUserList(query);
        List<SysUserExcelVO> sysUserExcelVOS = ConvertUtils.convertListTo(list, SysUserExcelVO::new);
        ExcelUtil<SysUserExcelVO> util = new ExcelUtil<>(SysUserExcelVO.class);
        return util.exportExcel(sysUserExcelVOS, "用户数据");
    }

    /**
     * 根据用户主键
     *
     * @param id 用户主键
     * @return SysUserVO
     */
    @Override
    public SysUserVO getById(Long id) {
        SysUserEntity entity = sysUserMapper.selectSysUserById(id);
        SysUserVO vo = ConvertUtils.convertTo(entity, SysUserVO::new);
        // 用户角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(id);
        vo.setRoleIdList(roleIdList);

        // 用户岗位列表
        List<Long> postIdList = sysUserPostService.getPostIdList(id);
        vo.setPostIdList(postIdList);

        // 城市回显
        List<String> city = new ArrayList<>();
        city.add(vo.getProvinceCode());
        city.add(vo.getCityCode());
        city.add(vo.getDistrictCode());
        vo.setCity(city);

        return vo;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return SysUserVO
     */
    @Override
    public SysUserVO info() {
        UserDetail userDetail = SecurityUser.getUser();
        SysUserVO user = ConvertUtils.convertTo(userDetail, SysUserVO::new);
        // 用户岗位列表
        assert user != null;
        List<Long> postIdList = sysUserPostService.getPostIdList(user.getId());
        user.setPostIdList(postIdList);

        // 用户岗位名称列表
        List<String> postNameList = sysPostService.getNameList(postIdList);
        user.setPostNameList(postNameList);

        // 用户角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(user.getId());
        user.setRoleIdList(roleIdList);

        // 用户岗位名称列表
        List<String> roleNameList = sysRoleService.getNameList(roleIdList);
        user.setRoleNameList(roleNameList);

        // 城市回显
        List<String> city = new ArrayList<>();
        city.add(user.getProvinceCode());
        city.add(user.getCityCode());
        city.add(user.getDistrictCode());
        user.setCity(city);

        return user;
    }

    /**
     * 根据id修改用户头像
     *
     */
    @Override
    public void updateUserAvatar(Long id, String avatar) {
        SysUserEntity entity = sysUserMapper.selectSysUserById(id);
        if(entity == null) {
            throw new ServiceException("用户信息不存在！");
        }
        sysUserMapper.updateUserAvatar(id, avatar);
    }

    /**
     * 根据用户主键
     *
     * @param id 用户主键
     * @return SysUserEntity
     */
    @Override
    public SysUserEntity selectSysUserById(Long id) {
        return sysUserMapper.selectSysUserById(id);
    }

    /**
     * 根据部门id查询用户信息集合
     *
     * @param deptId 部门id
     * @param sub 是否查询子集用户（1：查询；0不查询）
     * @return List<SysUserSelectVO>
     */
    @Override
    public List<SysUserSelectVO> getUserListByDeptId(Long deptId, Integer sub) {
        SysUserQuery query = new SysUserQuery();
        if(sub == 1){
            query.setDeptIds(getOrgCondition(deptId));
        }else {
            // Collections.singleton() 返回的集合是不可变的（无法添加/删除元素）。确保后续逻辑不需要修改该集合
            // 如果SysUserQuery内部或后续使用中需要修改集合，需保留HashSet方式
            // new HashSet<>(Arrays.asList(deptId)); // 可变集合
            query.setDeptIds(Collections.singleton(deptId));
        }
        List<SysUserEntity> list = sysUserMapper.selectSysUserList(query);
        return ConvertUtils.convertListTo(list, SysUserSelectVO::new);
    }

    /**
     * 通过用户ID集合查询用户集合
     *
     * @param userIds 用户ID集合
     * @return 用户对象信息集合
     */
    @Override
    public List<SysUserEntity> selectSysUserIds(Collection<Long> userIds) {
        return sysUserMapper.selectSysUserIds(userIds);
    }

    @Override
    public List<SysUserVO> list() {
        List<SysUserEntity> userEntities = sysUserMapper.selectSysUserList(null);
        return ConvertUtils.convertListTo(userEntities, SysUserVO::new);
    }

}
