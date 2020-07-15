package com.zk.mvp.base;

import java.io.Serializable;

public class Base implements Serializable {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
