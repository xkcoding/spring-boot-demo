package com.example.awaysuse.callmsg;

import com.example.awaysuse.result.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class UniformReponseHandler<T> {

    @ResponseStatus(HttpStatus.OK)
    public CallResultMsg sendSuccessResponse(){
        return new CallResultMsg(true, CodeAndMsg.SUCCESS.getCode(), CodeAndMsg.SUCCESS.getMsg(), null);
    }

    @ResponseStatus(HttpStatus.OK)
    public CallResultMsg sendSuccessResponse(T data) {
        return new CallResultMsg(true, CodeAndMsg.SUCCESS.getCode(), CodeAndMsg.SUCCESS.getMsg(), data);
    }

    @ExceptionHandler(UserDefinedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CallResultMsg sendErrorResponse_UserDefined(Exception exception){
        // return new CallResultMsg(false, ((UserDefinedException)exception).getException().getCode(), ((UserDefinedException)exception).getException().getMsg(), null);
        return new CallResultMsg(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);

    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public CallResultMsg badGatewayResponse(Exception exception){
        if (exception instanceof UserDefinedException) {
            return this.sendErrorResponse_UserDefined(exception);
        }

        return new CallResultMsg(false, CodeAndMsg.BAD_GATEWAY.getCode(),CodeAndMsg.BAD_GATEWAY.getMsg(),null);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CallResultMsg  notFoundErrorResponse_System(){
        return new CallResultMsg(false, CodeAndMsg.NOT_FOUND.getCode(),CodeAndMsg.NOT_FOUND.getMsg(),null);
    }
}
