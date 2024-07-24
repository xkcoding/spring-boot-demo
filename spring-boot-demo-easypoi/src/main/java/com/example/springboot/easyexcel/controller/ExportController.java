package com.example.springboot.easyexcel.controller;



import com.alibaba.excel.EasyExcel;
import com.example.springboot.easyexcel.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springboot.easyexcel.service.MemberService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController

public class ExportController {

    @Autowired
    private MemberService memberService;

    /**
     * 普通导出方式
     */
    @RequestMapping("/export1")
    public void exportMembers1(HttpServletResponse response) throws IOException {
        List<Member> members = memberService.getAllMember();

        // 设置文本内省
        response.setContentType("application/vnd.ms-excel");
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 设置响应头
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcel.write(response.getOutputStream(), Member.class).sheet("成员列表").doWrite(members);
    }

}

