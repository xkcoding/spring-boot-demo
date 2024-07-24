package com.example.awaysuse.callmsg;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
public enum CodeAndMsg{
    SUCCESS(1000, "SUCCESS"),
    METHODFAIL(2000, "ENCOUNTER AN ERROR WHEN EXECUTE METHOD"),
    NOT_FOUND(404, "Not Found"),
    UNKNOWEXCEPTION(3000, "THIS IS AN UNKNOW EXCEPTION"),
    BAD_GATEWAY(502, "Bad Gateway");

    private int code;
    private String msg;

    CodeAndMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
