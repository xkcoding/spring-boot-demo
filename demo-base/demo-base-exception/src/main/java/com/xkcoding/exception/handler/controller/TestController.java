package com.xkcoding.exception.handler.controller;

import com.xkcoding.common.enums.CommonStatus;
import com.xkcoding.common.model.viewmodel.Response;
import com.xkcoding.exception.handler.exception.JsonException;
import com.xkcoding.exception.handler.exception.PageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 模拟测试路由
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-20 02:11
 */
@Controller
public class TestController {

    @GetMapping("/json")
    @ResponseBody
    public Response<Void> jsonException() {
        throw new JsonException(CommonStatus.SERVER_ERROR);
    }

    @GetMapping("/page")
    public ModelAndView pageException() {
        throw new PageException(CommonStatus.SERVER_ERROR);
    }
}
