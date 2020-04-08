package com.xkcoding.exception.handler.controller;

import cn.hutool.core.lang.Dict;
import com.xkcoding.exception.handler.constant.Status;
import com.xkcoding.exception.handler.exception.BizException;
import com.xkcoding.exception.handler.exception.JsonException;
import com.xkcoding.exception.handler.exception.PageException;
import com.xkcoding.exception.handler.model.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 测试Controller
 * </p>
 *
 * @package: com.xkcoding.exception.handler.controller
 * @description: 测试Controller
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 8:49 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
public class TestController {

	@GetMapping("/json")
	@ResponseBody
	public ApiResponse jsonException() {
		throw new JsonException(Status.UNKNOWN_ERROR);
	}

	@GetMapping("/page")
	public ModelAndView pageException() {
		throw new PageException(Status.UNKNOWN_ERROR);
	}

    @GetMapping("/auto")
    public ApiResponse autoResponse() {
        Dict attr = Dict.create();
        attr.put("message", "I can return different content for different clients");
        throw new BizException(Status.NOT_FOUND, attr);
    }

}
