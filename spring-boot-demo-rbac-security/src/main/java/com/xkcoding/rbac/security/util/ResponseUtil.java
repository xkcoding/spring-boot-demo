package com.xkcoding.rbac.security.util;

import cn.hutool.json.JSONUtil;
import com.xkcoding.rbac.security.common.ApiResponse;
import com.xkcoding.rbac.security.common.IStatus;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * Response 通用工具类
 * </p>
 *
 * @package: com.xkcoding.rbac.security.util
 * @description: Response 通用工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:37
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class ResponseUtil {

    /**
     * 往 response 写出 json
     *
     * @param response 响应
     * @param status   状态
     * @param data     返回数据
     */
    public static void renderJson(HttpServletResponse response, IStatus status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            response.getWriter()
                    .write(JSONUtil.toJsonStr(ApiResponse.ofStatus(status, data)));
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }
}