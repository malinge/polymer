package com.polymer.framework.common.exception;

/**
 * 错误编码
 *
 * @author polymer
 */
public enum ErrorCode {
    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "还未授权，不能访问"),
    UNAUTHORIZED_ERROR(402, "您已在别处登录，请您修改密码或重新登录"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    NOT_FOUND(404, "请求未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),
    REFRESH_TOKEN_INVALID(406, "refresh_token已失效"),

    INTERNAL_SERVER_ERROR(500, "服务器异常，请稍后再试");

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }
}
