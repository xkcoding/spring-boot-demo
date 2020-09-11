package com.xkcoding.exception.handler.handler;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.xkcoding.exception.handler.exception.BaseException;
import com.xkcoding.exception.handler.exception.BizException;
import com.xkcoding.exception.handler.exception.JsonException;
import com.xkcoding.exception.handler.exception.PageException;
import com.xkcoding.exception.handler.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 统一异常处理
 * </p>
 *
 * @package: com.xkcoding.exception.handler.handler
 * @description: 统一异常处理
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 9:26 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DemoExceptionHandler {
    private static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 统一 json 异常处理
     *
     * @param exception JsonException
     * @return 统一返回 json 格式
     */
    @ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public ApiResponse jsonErrorHandler(JsonException exception) {
        log.error("【JsonException】:{}", exception.getMessage());
        return ApiResponse.ofException(exception);
    }

    /**
     * 统一 页面 异常处理
     *
     * @param exception PageException
     * @return 统一跳转到异常页面
     */
    @ExceptionHandler(value = PageException.class)
    public ModelAndView pageErrorHandler(PageException exception) {
        log.error("【DemoPageException】:{}", exception.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("message", exception.getMessage());
        view.setViewName(DEFAULT_ERROR_VIEW);
        return view;
    }

    /**
     * 定义状态码和模板路径对应关系
     */
    private final Map<Integer, String> errorPageMap = new HashMap<>();

    @PostConstruct
    public void init() {
        errorPageMap.put(404, "/4xx");
        errorPageMap.put(500, "/5xx");
    }

    /**
     * 自适应返回 异常处理
     *
     * @param exception BizException
     * @param request   request
     * @param response  response
     * @throws IOException
     * @throws ServletException
     */
    @ExceptionHandler(value = {BizException.class})
    public void autoHandler(BizException exception, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.error("【BizException】: {}", exception.getMessage());
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));

        boolean showJson = (userAgent.isMobile() && exception.isJson()) || (!userAgent.isMobile() && !exception.isView());

        if (showJson) {
            write(exception, response);
        } else {
            write(exception, request, response);
        }

    }

    /**
     * 输出json到客户端
     *
     * @param exception {@link BaseException}的子类
     * @param response  response
     * @throws IOException
     */
    private void write(BaseException exception, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(ApiResponse.ofException(exception, exception.getData()).toString());
        writer.flush();
        writer.close();
    }

    /**
     * 输出视图到客户端
     *
     * @param exception {@link BaseException}的子类
     * @param response  response
     * @throws IOException
     * @throws ServletException
     */
    private void write(BaseException exception, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        exception.getAttributes().forEach(request::setAttribute);
        request.getRequestDispatcher(getErrorPage(exception.getCode())).forward(request, response);
    }

    /**
     * 根据抛出状态码定位到视图路径，若未匹配到，则使用统一异常页面
     *
     * @param code 状态码
     * @return 视图路径
     */
    private String getErrorPage(Integer code) {
        return errorPageMap.getOrDefault(code, "/error");
    }

}
