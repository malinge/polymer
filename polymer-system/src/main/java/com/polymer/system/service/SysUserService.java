package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.entity.SysUserEntity;
import com.polymer.system.query.SysRoleUserQuery;
import com.polymer.system.query.SysUserQuery;
import com.polymer.system.vo.SysUserBaseVO;
import com.polymer.system.vo.SysUserSelectVO;
import com.polymer.system.vo.SysUserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * 用户管理
 *
 * @author polymer
 */
public interface SysUserService {

    /**
     * 根据用户查询获取分页用户列表
     *
     * @param query 用户查询
     * @return PageResult<SysUserVO>
     */
    PageResult<SysUserVO> page(SysUserQuery query);

    /**
     * 保存用户信息
     *
     * @param vo 用户信息
     */
    SysUserVO save(SysUserVO vo);

    /**
     * 更新用户信息
     *
     * @param vo 用户信息
     */
    SysUserVO update(SysUserVO vo);

    /**
     * 更新登录用户信息
     *
     * @param vo 用户信息
     */
    void updateLoginInfo(SysUserBaseVO vo);

    /**
     * 根据用户信息主键集合删除用户信息
     *
     * @param idList 用户信息主键集合
     */
    void delete(List<Long> idList);

    /**
     * 根据手机号查询用户信息
     *
     * @param mobile 手机号
     * @return SysUserVO
     */
    SysUserVO getByMobile(String mobile);

    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     */
    void updatePassword(Long id, String newPassword);

    /**
     * 分配角色，用户列表
     *
     * @param query 分配角色查询
     * @return PageResult<SysUserVO>
     */
    PageResult<SysUserVO> roleUserPage(SysRoleUserQuery query);

    /**
     * 批量导入用户
     *
     * @param file     excel文件
     * @param password 密码
     * @return String
     */
    String importByExcel(MultipartFile file, String password) throws Exception;

    /**
     * 导出用户信息表格
     *
     * @return byte[]
     */
    byte[] export(SysUserQuery query);

    /**
     * 根据用户主键
     *
     * @param id 用户主键
     * @return SysUserVO
     */
    SysUserVO getById(Long id);

    /**
     * 获取当前登录用户信息
     *
     * @return SysUserVO
     */
    SysUserVO info();

    /**
     * 根据id修改用户头像
     *
     */
    void updateUserAvatar(Long id, String avatar);

    /**
     * 根据用户主键
     *
     * @param id 用户主键
     * @return SysUserEntity
     */
    SysUserEntity selectSysUserById(Long id);

    /**
     * 根据部门id查询用户信息集合
     *
     * @param deptId 部门id
     * @param sub 是否查询子集用户（1：查询；0不查询）
     * @return List<SysUserSelectVO>
     */
    List<SysUserSelectVO> getUserListByDeptId(Long deptId, Integer sub);

    /**
     * 通过用户ID集合查询用户集合
     *
     * @param userIds 用户ID集合
     * @return 用户对象信息集合
     */
    List<SysUserEntity> selectSysUserIds(Collection<Long> userIds);

    /**
     * 获取全部用户集合
     * @return SysUserVO
     */
    List<SysUserVO> list();
}
