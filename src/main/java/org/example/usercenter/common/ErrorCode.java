package org.example.usercenter.common;

/**
 * @author LinZeyuan
 * @description 异常错误码
 * @createDate 2025/10/10 11:35
 */
public enum ErrorCode {

    SUCCESS(20000, "成功", ""),

    PARAMS_ERROR(40000, "请求参数错误", ""),

    NULL_ERROR(40001, "请求数据为空", ""),

    NOT_LOGIN(40100, "未登录", ""),

    NO_AUTH(40101, "无权限", ""),

    SYSTEM_ERROR(50000, "系统内部异常", "");

    private final int code; //状态码
    private final String message; //状态码信息
    private final String description; //状态码描述

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
