package com.lin.imissyou.exception;

public class ForbiddenException extends HttpException {

    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }



}
