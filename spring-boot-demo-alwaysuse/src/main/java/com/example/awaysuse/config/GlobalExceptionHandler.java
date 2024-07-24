package com.example.awaysuse.config;

import com.example.awaysuse.response.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author fengh
 * @Date 2020/9/14 15:51
 * @Description 全局统一异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseResult<String> errorHandler(HttpServletRequest request, NoHandlerFoundException exception, HttpServletResponse response) {
        return ResponseResult.error(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    /**
     * 405异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<String> errorHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException exception, HttpServletResponse response) {
        return ResponseResult.error(HttpStatus.METHOD_NOT_ALLOWED.value(), exception.getMessage());
    }

    /**
     * 415异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult<String> errorHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException exception, HttpServletResponse response) {
        return ResponseResult.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), exception.getMessage());
    }

    /**
     * 500异常处理
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseResult<String> errorHandler(HttpServletRequest request, HttpServerErrorException exception, HttpServletResponse response) {
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    /**
     * 参数校验错误处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<String> errorHandler(HttpServletRequest request, MethodArgumentNotValidException exception, HttpServletResponse response) {
        List<String> errors = exception.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        return ResponseResult.error(0, String.join(",", errors));
    }

    /**
     * 普通异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> errorHandler(HttpServletRequest request, Exception exception, HttpServletResponse response) {
        return ResponseResult.error(0, exception.getMessage());
    }


}
