package com.lin.imissyou.core.enumeration;

public enum  LoginType {
    //微信登录
    USER_WX(0, "微信登录"),
    //邮箱登录
    USER_Email(1, "邮箱登录");

    private Integer value;
    private String desctiption;

    LoginType(Integer value, String desctiption) {
        this.value = value;
        this.desctiption = desctiption;
    }
}
