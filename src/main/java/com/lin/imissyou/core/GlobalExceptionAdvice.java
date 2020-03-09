package com.lin.imissyou.core;

import com.lin.imissyou.core.configuration.ExceptionCodeConfiguration;
import com.lin.imissyou.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration codes;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest request, Exception e) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        UnifyResponse response = new UnifyResponse(9999,"服务器异常",method +" "+url);
        return response;
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest request, HttpException e) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        UnifyResponse message = new UnifyResponse(e.getCode(),codes.getMessage(e.getCode()),method +" "+url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> r = new ResponseEntity<UnifyResponse>(message,headers,status);
        return r;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest request, MethodArgumentNotValidException e) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String messages = this.formatAllErrorMessages(errors);
        return new UnifyResponse(10001,messages,method +" "+url);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintException(HttpServletRequest request, ConstraintViolationException e) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        String message = e.getMessage();
        return new UnifyResponse(10001,message,method +" "+url);
    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer buffer = new StringBuffer();
        errors.forEach(error->{
            buffer.append(error.getDefaultMessage()).append(";");
        });
        return buffer.toString();
    }

}
