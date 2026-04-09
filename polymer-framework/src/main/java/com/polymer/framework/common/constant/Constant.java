package com.polymer.framework.common.constant;

/**
 * 常量
 *
 * @author polymer
 */
public interface Constant {
    /**
     * 根节点标识
     */
    Long ROOT = 0L;

    /**
     * 超级管理员
     */
    Integer SUPER_ADMIN = 1;
    /**
     * 禁用
     */
    Integer DISABLE = 0;
    /**
     * 启用
     */
    Integer ENABLE = 1;
    /**
     * 失败
     */
    Integer FAIL = 0;
    /**
     * 成功
     */
    Integer SUCCESS = 1;
    /**
     * OK
     */
    String OK = "OK";

    /**
     * pgsql的driver
     */
    String PGSQL_DRIVER = "org.postgresql.Driver";

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";


    /**
     * 默认表别名设置
     */
    String DEFAULT_ALIAS = ".";

    /**
     * 默认数据权限表字段名
     */
    String DEFAULT_DATA_FIELD = "dept_id";
    /**
     * 默认创建人字段名
     */
    String CREATOR_FIELD = "creator";

}