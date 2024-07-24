package com.example.awaysuse.controller;


import com.example.awaysuse.callmsg.CallResultMsg;
import com.example.awaysuse.callmsg.CodeAndMsg;
import com.example.awaysuse.callmsg.UniformReponseHandler;
import com.example.awaysuse.callmsg.UserDefinedException;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//  测试封装返回的结果集
@RestController
public class TestCallMsgResult {

    @GetMapping("/doTestObject")
    public CallResultMsg testObjectReturn() {
        Map<String, Integer> map = new HashMap();
        map.put("qingfen", 16);
        map.put("lantian", 17);
        map.put("baiyun", 18);
        CallResultMsg callResultMsg = new UniformReponseHandler<Map>().sendSuccessResponse(map);
        System.out.println(CodeAndMsg.SUCCESS);
        return callResultMsg;
    }


    @GetMapping("/doTestException/{x}")
    public int testExceptionResturn(@PathVariable int x) {
        /*if (0 < x && x < 10) {
            // throw new UserDefinedException(CodeAndMsg.METHODFAIL);
            throw new UserDefinedException(CodeAndMsg.METHODFAIL);
        } else {
            return 1 / 0;
        }*/
        return  1/0;
    }



}
