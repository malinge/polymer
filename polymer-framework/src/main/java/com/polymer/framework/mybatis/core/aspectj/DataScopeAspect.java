package com.polymer.framework.mybatis.core.aspectj;

import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.common.pojo.PageParam;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.framework.mybatis.core.enums.DataScopeEnum;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 数据过滤处理
 *
 * @author polymer
 */
@Aspect
@Component
public class DataScopeAspect {

    @Pointcut("@annotation(com.polymer.framework.mybatis.core.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        // 获得注解
        DataScope controllerDataScope = getAnnotationLog(point);
        if (controllerDataScope != null) {
            // 获取当前的用户及相关属性，需提前获取和保存数据权限对应的部门ID集合
            UserDetail user = SecurityUser.getUser();
            if (user != null) {
                String sqlSegment = getSqlSegment(controllerDataScope, user);
                if (StringUtils.isNotBlank(sqlSegment)) {
                    Object params = point.getArgs()[0];
                    if (params instanceof PageParam) {
                        PageParam pageParam = (PageParam) params;
                        pageParam.setDataScope(sqlSegment);
                    }
                }
            }
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }

    /**
     * 拼接需要在业务 SQL 中额外追加的数据权限 SQL
     *
     * @param annotation 注解
     * @return 数据权限 SQL
     */
    private String getSqlSegment(DataScope annotation, UserDetail user) {
        // 如果是超级管理员，则不进行数据过滤
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            return null;
        }
        // 数据范围
        Integer dataScope = user.getDataScope();
        // 数据权限范围
        List<Long> dataScopeList = user.getDataScopeList();

        // 全部数据权限
        if (Objects.equals(dataScope, DataScopeEnum.ALL.getValue())) {
            return null;
        }

        // 过滤字段
        String dataId = annotation.dataId();
        // 获取表的别名
        String alias = annotation.alias();

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");
        if(Objects.equals(dataScope, DataScopeEnum.SELF.getValue())){ // 查询本人数据
            sqlFilter.append(alias).append(Constant.CREATOR_FIELD).append("=").append(user.getId());
        }else if(Objects.equals(dataScope, DataScopeEnum.DEPT_ONLY.getValue())){ // 查询部门数据
            sqlFilter.append(alias).append(dataId).append("=").append(dataScopeList.get(0));
        }else if(Objects.equals(dataScope, DataScopeEnum.DEPT_AND_CHILD.getValue())
                || Objects.equals(dataScope, DataScopeEnum.CUSTOM.getValue())){ // 查询本部门及子部门数据数据和自定义数据
            sqlFilter.append(alias).append(dataId);
            String inSql = dataScopeList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "IN (", ")"));

            sqlFilter.append(" ").append(inSql);
        }else {
            sqlFilter.append(alias).append(Constant.CREATOR_FIELD).append("=").append(user.getId());

        }
        return sqlFilter.append(")").toString();
    }
}
