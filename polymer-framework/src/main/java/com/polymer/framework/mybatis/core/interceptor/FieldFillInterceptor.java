package com.polymer.framework.mybatis.core.interceptor;

import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class FieldFillInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];
        if (SqlCommandType.INSERT.equals(ms.getSqlCommandType())) {
            insertParamCheck(param);

        } else if (SqlCommandType.UPDATE.equals(ms.getSqlCommandType())) {
            updateParamCheck(param);
        }
        return invocation.proceed();
    }

    @SuppressWarnings("unchecked")
    private void insertParamCheck(Object param) throws Throwable {
        if (param instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> map = (MapperMethod.ParamMap) param;
            if(map.containsKey("list")){
                List<Object> list = (List<Object>) map.get("list");
                for (Object paramObj : list) {
                    insertAssignment(paramObj);
                }
            }else {
                insertAssignment(param);
            }
        } else {
            insertAssignment(param);
        }
    }

    @SuppressWarnings("unchecked")
    private void updateParamCheck(Object param) throws Throwable {
        if (param instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> map = (MapperMethod.ParamMap) param;
            if(map.containsKey("list")){
                List<Object> list = (List<Object>) map.get("list");
                for (Object paramObj : list) {
                    updateAssignment(paramObj);
                }
            }else {
                updateAssignment(param);
            }
        } else {
            updateAssignment(param);
        }
    }

    private void insertAssignment(Object param) throws Throwable {
        Class<?> entityClass = param.getClass();
        UserDetail userDetail = SecurityUser.getUser();
        if (userDetail == null) {
            userDetail = new UserDetail();
        }
        // 使用反射检查实体类是否包含指定的属性
        if (hasProperty(entityClass, "createTime")) {
            entityClass.getMethod("setCreateTime", LocalDateTime.class).invoke(param, LocalDateTime.now());
        }
        if (hasProperty(entityClass, "creator")) {

            entityClass.getMethod("setCreator", Long.class).invoke(param, userDetail.getId());
        }
        if (hasProperty(entityClass, "deptId")) {
            Object getVersion = entityClass.getMethod("getDeptId").invoke(param);
            if (getVersion == null) {
                entityClass.getMethod("setDeptId", Long.class).invoke(param, userDetail.getDeptId());
            }
        }
    }

    private void updateAssignment(Object param) throws Throwable {
        Class<?> entityClass = param.getClass();
        // 使用反射检查实体类是否包含指定的属性
        if (hasProperty(entityClass, "updateTime")) {
            entityClass.getMethod("setUpdateTime", LocalDateTime.class).invoke(param, LocalDateTime.now());
        }
        if (hasProperty(entityClass, "updater")) {
            entityClass.getMethod("setUpdater", Long.class).invoke(param, SecurityUser.getUserId());
        }
    }

    private boolean hasProperty(Class<?> paramClass, String propertyName) {
        Map<String, Field> fieldMap = getAllClassFields(paramClass);
        return fieldMap.containsKey(propertyName);
    }

    private Map<String, Field> getAllClassFields(Class<?> clazz) {
        Map<String, Field> resutlMap = new LinkedHashMap<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                resutlMap.put(field.getName(), field);
            }
        }
        return resutlMap;
    }
}
