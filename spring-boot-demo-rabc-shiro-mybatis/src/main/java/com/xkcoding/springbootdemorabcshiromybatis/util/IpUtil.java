package com.xkcoding.springbootdemorabcshiromybatis.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 获取 IP 工具类
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.util
 * @description： 获取 IP 工具类
 * @author: yangkai.shen
 * @date: Created in 2017/12/7 上午11:13
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Slf4j
public class IpUtil {
	public final static String ERROR_IP = "127.0.0.1";

	public final static Pattern pattern = Pattern.
			compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

	/**
	 * 取外网IP
	 *
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("x-real-ip");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		//过滤反向代理的ip
		String[] stemps = ip.split(",");
		if (stemps != null && stemps.length >= 1) {
			//得到第一个IP，即客户端真实IP
			ip = stemps[0];
		}

		ip = ip.trim();
		if (ip.length() > 23) {
			ip = ip.substring(0, 23);
		}

		return ip;
	}

	/**
	 * 获取用户的真实ip
	 *
	 * @param request
	 * @return
	 */
	public static String getUserIP(HttpServletRequest request) {

		// 优先取X-Real-IP
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if ("0:0:0:0:0:0:0:1".equals(ip)) {
				ip = ERROR_IP;
			}
		}

		if ("unknown".equalsIgnoreCase(ip)) {
			ip = ERROR_IP;
			return ip;
		}

		int pos = ip.indexOf(',');
		if (pos >= 0) {
			ip = ip.substring(0, pos);
		}

		return ip;
	}

	public static String getLastIpSegment(HttpServletRequest request) {
		String ip = getUserIP(request);
		if (ip != null) {
			ip = ip.substring(ip.lastIndexOf('.') + 1);
		} else {
			ip = "0";
		}
		return ip;
	}

	public static boolean isValidIP(HttpServletRequest request) {
		String ip = getUserIP(request);
		return isValidIP(ip);
	}

	/**
	 * 判断我们获取的ip是否是一个符合规则ip
	 *
	 * @param ip
	 * @return
	 */
	public static boolean isValidIP(String ip) {
		if (StringUtils.isEmpty(ip)) {
			log.debug("ip is null. valid result is false");
			return false;
		}

		Matcher matcher = pattern.matcher(ip);
		boolean isValid = matcher.matches();
		log.debug("valid ip:" + ip + " result is: " + isValid);
		return isValid;
	}

	public static String getLastServerIpSegment() {
		String ip = getServerIP();
		if (ip != null) {
			ip = ip.substring(ip.lastIndexOf('.') + 1);
		} else {
			ip = "0";
		}
		return ip;
	}

	public static String getServerIP() {
		InetAddress inet;
		try {
			inet = InetAddress.getLocalHost();
			String hostAddress = inet.getHostAddress();
			return hostAddress;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "127.0.0.1";
	}
}
