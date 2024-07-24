package com.example.awaysuse.callmsg;

import com.example.awaysuse.result.ResultCode;


public class UserDefinedException extends RuntimeException {
    private CodeAndMsg exception;

    public UserDefinedException(CodeAndMsg exception){
        this.exception = exception;
    }

    public CodeAndMsg getException() {
        return exception;
    }

    public void setException(CodeAndMsg exception) {
        this.exception = exception;
    }
}
