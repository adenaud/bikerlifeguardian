package com.bikerlifeguardian;

public class LoginResult {
    public static final int ERROR = -1;
    public static final int LOGIN_OK = 0;
    public static final int INVALID_USERNAME = 2;
    public static final int INVALID_PASSWORD = 1;

    private String uuid;
    private int code;

    public LoginResult(){};

    public LoginResult(String uuid, int code) {
        this.uuid = uuid;
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}