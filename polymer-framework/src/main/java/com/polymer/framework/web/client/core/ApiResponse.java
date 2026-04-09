package com.polymer.framework.web.client.core;


public class ApiResponse<T> {
    private int status;     // 业务状态码
    private T result;           // 响应数据（泛型）
    private String message;     // 错误消息

    // 检查是否成功的快捷方法
    public boolean isSuccess() {
        return status == 200;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
