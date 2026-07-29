package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.api.system.SysCheckImportApi;
import com.polymer.api.system.dto.ImportResultDTO;
import com.polymer.api.system.vo.ImportResultVO;
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
import com.polymer.framework.common.enums.SuperAdminEnum;
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
import com.polymer.system.vo.SysDeptVO;
import com.polymer.system.vo.SysUserBaseVO;
import com.polymer.system.vo.SysUserErrorExcelVO;
import com.polymer.system.vo.SysUserExcelVO;
import com.polymer.system.vo.SysUserSelectVO;
import com.polymer.system.vo.SysUserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author polymer
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    public static final String BUSINESS_TYPE = "user";
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
    @Resource
    private SysCheckImportApi sysCheckImportApi;

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
    @Transactional(rollbackFor = Exception.class)
    public ImportResultVO importByExcel(MultipartFile file, String password, String strategy) throws Exception {
        byte[] fileBytes = file.getBytes();
        String fileName = file.getOriginalFilename();

        // 1. 校验导入文件
        ImportResultDTO<SysUserExcelVO> validationResult = sysCheckImportApi.validateImportFile(fileBytes, fileName,
                SysUserExcelVO.class, strategy, BUSINESS_TYPE);

        // 表头错误，直接返回
        if (!validationResult.getPassed()) {
            return ConvertUtils.convertTo(validationResult, ImportResultVO::new);
        }

        List<SysUserExcelVO> dataList = validationResult.getDataList();
        if (dataList == null || dataList.isEmpty()) {
            return ConvertUtils.convertTo(validationResult, ImportResultVO::new);
        }

        // 2. 数据校验和导入
        int totalCount = dataList.size(), successNum = 0, errorNum = 0;
        int skipNum = 0, overrideNum = 0, conflictHandleCount = 0;

        // 待插入数据列表
        List<SysUserVO> insertDataList = new ArrayList<>();
        // 待更新数据列表（非空字段更新）
        List<SysUserVO> updateDataList = new ArrayList<>();
        // 待覆盖数据列表（包含null和空串）
        List<SysUserVO> overrideDataList = new ArrayList<>();
        // 错误数据集合
        List<SysUserErrorExcelVO> errorDataList = new ArrayList<>();

        // 预查询所有已存在的用户名和手机号，用于批量判断
        Set<String> existingUsernames = new HashSet<>();
        Set<String> existingMobiles = new HashSet<>();
        Map<String, SysUserEntity> usernameToUserMap = new HashMap<>();
        Map<String, SysUserEntity> mobileToUserMap = new HashMap<>();

        // 获取所有已存在的用户信息
        List<SysUserEntity> allExistingUsers = sysUserMapper.selectSysUserList(null);
        for (SysUserEntity user : allExistingUsers) {
            existingUsernames.add(user.getUsername());
            existingMobiles.add(user.getMobile());
            usernameToUserMap.put(user.getUsername(), user);
            mobileToUserMap.put(user.getMobile(), user);
        }

        for (SysUserExcelVO excelVO : dataList) {
            StringBuilder errorMsg = new StringBuilder();
            // 2.1 必填项校验
            validateRequiredFields(excelVO, errorMsg);

            // 2.2 如果有错误，记录
            if (errorMsg.length() > 0) {
                errorDataList.add(buildErrorData(excelVO, errorMsg.toString()));
                errorNum++;
                continue;
            }

            SysDeptVO deptVO = sysDeptService.getById(excelVO.getDeptId());
            if(deptVO == null){
                errorNum++;
                errorDataList.add(buildErrorData(excelVO, "部门id错误"));
                continue;
            }

            String username = excelVO.getUsername();
            String mobile = excelVO.getMobile();
            boolean usernameExists = existingUsernames.contains(username);
            boolean mobileExists = existingMobiles.contains(mobile);

            // 2.3 处理用户名存在的情况
            if (usernameExists) {
                SysUserEntity existingUser = usernameToUserMap.get(username);
                // 如果用户名存在但手机号不同，检查手机号是否被其他用户占用
                if (mobileExists && !existingUser.getMobile().equals(mobile)) {
                    // 手机号被其他用户占用
                    errorDataList.add(buildErrorData(excelVO, "手机号已被其他用户占用"));
                    errorNum++;
                    continue;
                }

                if ("skip".equals(strategy)) {
                    skipNum++;
                    conflictHandleCount++;
                    errorDataList.add(buildErrorData(excelVO, "用户名已存在，执行跳过操作"));
                    errorNum++;
                    continue;
                } else if ("override".equals(strategy)) {
                    overrideNum++;
                    conflictHandleCount++;
                    // 覆盖用户信息（包含null和空串也更新）
                    overrideDataList.add(buildVoDataForUpdate(excelVO,  existingUser.getId()));
                    successNum++;
                    continue;
                } else {
                    // "update" 策略：更新（仅非空字段）
                    updateDataList.add(buildVoDataForUpdate(excelVO,  existingUser.getId()));
                    successNum++;
                    conflictHandleCount++;
                    continue;
                }
            }

            // 2.4 处理手机号存在的情况（用户名不存在）
            if (mobileExists) {
                SysUserEntity existingUser = mobileToUserMap.get(mobile);
                // 检查用户名是否被其他用户占用（理论上用户名已检查不存在）
                if ("skip".equals(strategy)) {
                    skipNum++;
                    conflictHandleCount++;
                    errorDataList.add(buildErrorData(excelVO, "手机号已存在，执行跳过操作"));
                    errorNum++;
                    continue;
                } else if ("override".equals(strategy)) {
                    overrideNum++;
                    conflictHandleCount++;
                    // 覆盖用户（包含null和空串也更新）
                    overrideDataList.add(buildVoDataForUpdate(excelVO, existingUser.getId()));
                    successNum++;
                    continue;
                } else {
                    // "update" 策略：更新（仅非空字段）
                    updateDataList.add(buildVoDataForUpdate(excelVO, existingUser.getId()));
                    successNum++;
                    conflictHandleCount++;
                    continue;
                }
            }

            // 2.5 新增用户
            insertDataList.add(buildVoData(excelVO, password));
            successNum++;
        }

        // 批量插入
        if (!insertDataList.isEmpty()) {
            batchInsertSysUser(insertDataList);
        }
        // 批量更新
        if(!updateDataList.isEmpty()){
            batchUpdateSysUser(updateDataList);
        }
        // 批量覆盖
        if(!overrideDataList.isEmpty()){
            batchUpdateSysUserFull(overrideDataList);
        }

        // 3. 导入结果处理
        return sysCheckImportApi.importResultProcessing(
                SysUserErrorExcelVO.class,
                errorDataList, totalCount, successNum, errorNum, overrideNum, skipNum, conflictHandleCount,
                strategy, validationResult.getResultFileUrl(), BUSINESS_TYPE);
    }

    /**
     * 构建VO数据（用于覆盖，包含null和空串也更新）
     */
    private SysUserVO buildVoDataForUpdate(SysUserExcelVO excelVO, Long existingUserId) {
        SysUserVO vo = ConvertUtils.convertTo(excelVO, SysUserVO::new);
        vo.setId(existingUserId);
        return vo;
    }

    /**
     * 构建保存数据对象
     */
    private SysUserVO buildVoData(SysUserExcelVO excelVO, String password) {
        SysUserVO vo = ConvertUtils.convertTo(excelVO, SysUserVO::new);
        vo.setPassword(password);
        vo.setSuperAdmin(SuperAdminEnum.NO.getValue());
        return vo;
    }

    /**
     * 构建错误数据对象
     */
    private SysUserErrorExcelVO buildErrorData(SysUserExcelVO excelVO, String errorReason) {
        SysUserErrorExcelVO errorVO = ConvertUtils.convertTo(excelVO, SysUserErrorExcelVO::new);
        errorVO.setErrorReason(errorReason);
        return errorVO;
    }

    /**
     * 校验必填字段
     */
    private void validateRequiredFields(SysUserExcelVO excelVO, StringBuilder errorMsg) {
        if (StringUtils.isBlank(excelVO.getUsername())) {
            errorMsg.append("用户账号不能为空；");
        }
        if (StringUtils.isBlank(excelVO.getRealName())) {
            errorMsg.append("用户姓名不能为空；");
        }
        if (excelVO.getGender() == null) {
            errorMsg.append("用户性别不能为空；");
        }
        if (StringUtils.isBlank(excelVO.getMobile())) {
            errorMsg.append("手机号码不能为空；");
        }
        if (excelVO.getStatus() == null) {
            errorMsg.append("用户状态不能为空；");
        }
        if (excelVO.getDeptId() == null) {
            errorMsg.append("部门编号不能为空；");
        }
    }

    /**
     * 导出用户信息表格
     *
     * @return byte[]
     */
    @Override
    public byte[] export(SysUserQuery query) {
        List<SysUserEntity> list = sysUserMapper.selectSysUserList(query);
        List<SysUserExcelVO> exportVOS = ConvertUtils.convertListTo(list, SysUserExcelVO::new);
        ExcelUtil<SysUserExcelVO> util = new ExcelUtil<>(SysUserExcelVO.class);
        byte[] bytes = util.exportExcel(exportVOS, "用户数据", "用户数据");
        // 保存导出记录
        int totalCount = 0;
        if(list != null){
            totalCount = list.size();
        }
        sysCheckImportApi.saveExportResult(bytes, totalCount, BUSINESS_TYPE);
        return bytes;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsertSysUser(List<SysUserVO> list) {
        List<SysUserEntity> entityList = ConvertUtils.convertListTo(list, SysUserEntity::new);
        return batchUtils.executeBatch(SysUserMapper.class, entityList, SysUserMapper::insertSysUser);
    }

    @Override
    public int batchUpdateSysUser(List<SysUserVO> list) {
        List<SysUserEntity> entityList = validEntities(list);
        return batchUtils.executeBatch(SysUserMapper.class, entityList, SysUserMapper::updateSysUser);
    }

    @Override
    public int batchUpdateSysUserFull(List<SysUserVO> list) {
        List<SysUserEntity> entityList = validEntities(list);
        return batchUtils.executeBatch(SysUserMapper.class, entityList, SysUserMapper::updateSysUserFull);
    }

    @Override
    public SysUserVO getByUsername(String username) {
        SysUserEntity entity = sysUserMapper.getByUsername(username);
        return ConvertUtils.convertTo(entity, SysUserVO::new);
    }

    private List<SysUserEntity> validEntities(List<SysUserVO> list){
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<SysUserEntity> entityList = ConvertUtils.convertListTo(list, SysUserEntity::new);
        // 过滤出有ID的实体
        return entityList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toList());
    }

}
