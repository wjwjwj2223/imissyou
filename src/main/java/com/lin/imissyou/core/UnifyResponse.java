package com.lin.imissyou.core;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnifyResponse<T> {
    private int code;
    private String message;
    private String request;
    private T data;

    public UnifyResponse(int code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

    public UnifyResponse(int code, String message, String request, T data) {
        this.code = code;
        this.message = message;
        this.request = request;
        this.data = data;
    }
}
