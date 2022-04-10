package com.strongduanmu.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseEnum {
    
    SUCCESS("0", "操作成功"),
    UNKNOW_ERROR("-1", "未知错误"),
    ERROR("999", "请求处理失败"),
    LOGIN_FIELD("1009", "用户名或者密码错误");
    
    private final String code;
    
    private final String message;
}
