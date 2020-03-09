package com.lin.imissyou.exception;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatusCode = 500;
}
