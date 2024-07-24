package com.example.awaysuse.controller;

import com.example.awaysuse.model.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserInfoController {

    final UserInfoEntity userInfo;

    @RequestMapping("getUser")
    public String getUserInfo(){
          log.info(userInfo.getName());
          int i=2;
          return userInfo.getName();
    }

    public static void main(String args[]) {
        String str = "fengxb@163.com";
        String pattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println("正则"+m.matches());
    }

}
