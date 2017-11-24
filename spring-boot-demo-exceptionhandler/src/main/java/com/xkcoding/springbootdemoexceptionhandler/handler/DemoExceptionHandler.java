package com.xkcoding.springbootdemoexceptionhandler.handler;

import com.xkcoding.springbootdemoexceptionhandler.domain.R;
import com.xkcoding.springbootdemoexceptionhandler.exception.DemoJsonException;
import com.xkcoding.springbootdemoexceptionhandler.exception.DemoPageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义异常统一处理
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler.handler
 * @description： 自定义异常统一处理
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午1:37
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@ControllerAdvice
@Slf4j
public class DemoExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";

	/**
	 * 统一 json 异常处理
	 *
	 * @param exception DemoJsonException
	 * @return 统一返回 json 格式
	 */
	@ExceptionHandler(value = DemoJsonException.class)
	@ResponseBody
	public R jsonErrorHandler(DemoJsonException exception) {
		log.error("【DemoJsonException】:{}", exception.getMessage());
		return R.error(exception);
	}

	/**
	 * 统一 页面 异常处理
	 *
	 * @param exception DemoPageException
	 * @return 统一跳转到异常页面
	 */
	@ExceptionHandler(value = DemoPageException.class)
	public ModelAndView pageErrorHandler(DemoPageException exception) {
		log.error("【DemoPageException】:{}", exception.getMessage());
		ModelAndView view = new ModelAndView();
		view.addObject("message", exception.getMessage());
		view.setViewName(DEFAULT_ERROR_VIEW);
		return view;
	}
}
