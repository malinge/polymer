package com.polymer.framework.web.websocket.core;

import java.io.Serializable;

public class NotifyMessageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private Object data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
