package com.polymer.framework.common.exception;

/**
 * 自定义异常
 *
 * @author polymer
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ServiceException(String msg) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public ServiceException(int statusCode, String message) {
        super(message);
        this.code = statusCode;
        this.msg = message;
    }

}