package com.xkcoding.log.aop.aspectj;

import cn.hutool.json.JSONUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 使用 aop 切面记录请求日志信息
 * </p>
 *
 * @package: com.xkcoding.log.aop.aspectj
 * @description: 使用 aop 切面记录请求日志信息
 * @author: yangkai.shen
 * @date: Created in 2018/10/1 10:05 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Aspect
@Component
@Slf4j
public class AopLog {
	private static final String START_TIME = "request-start";

	/**
	 * 切入点
	 */
	@Pointcut("execution(public * com.xkcoding.log.aop.controller.*Controller.*(..))")
	public void log() {

	}

	/**
	 * 前置操作
	 *
	 * @param point 切入点
	 */
	@Before("log()")
	public void beforeLog(JoinPoint point) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		log.info("【请求 URL】：{}", request.getRequestURL());
		log.info("【请求 IP】：{}", request.getRemoteAddr());
		log.info("【请求类名】：{}，【请求方法名】：{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());

		Map<String, String[]> parameterMap = request.getParameterMap();
		log.info("【请求参数】：{}，", JSONUtil.toJsonStr(parameterMap));
		Long start = System.currentTimeMillis();
		request.setAttribute(START_TIME, start);
	}

	/**
	 * 环绕操作
	 *
	 * @param point 切入点
	 * @return 原方法返回值
	 * @throws Throwable 异常信息
	 */
	@Around("log()")
	public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
		Object result = point.proceed();
		log.info("【返回值】：{}", JSONUtil.toJsonStr(result));
		return result;
	}

	/**
	 * 后置操作
	 */
	@AfterReturning("log()")
	public void afterReturning() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		Long start = (Long) request.getAttribute(START_TIME);
		Long end = System.currentTimeMillis();
		log.info("【请求耗时】：{}毫秒", end - start);

		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		log.info("【浏览器类型】：{}，【操作系统】：{}，【原始User-Agent】：{}", userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), header);
	}
}
