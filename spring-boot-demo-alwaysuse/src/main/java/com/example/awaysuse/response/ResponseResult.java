package com.example.awaysuse.response;

import lombok.Data;

/**
 * @Author feng
 * @Date 2020/9/10 17:01
 * @Description
 */
@Data
public class ResponseResult<T> {

    private Integer code = 200;
    private String message = "success";
    private T data;

    public ResponseResult(){

    }

    public ResponseResult(T data){
        this.data = data;
    }

    public ResponseResult(int code, String message){
        this.code = code;
        this.message = message;
    }

    public ResponseResult(String message, T data){
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseResult<T> success(){
        return new ResponseResult<T>();
    }

    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult<T>(data);
    }

    public static <T> ResponseResult<T> success(String message, T data){
        return new ResponseResult<T>(message, data);
    }

    public static <T> ResponseResult<T> error(int code, String message){
        return new ResponseResult<T>(code, message);
    }

}
