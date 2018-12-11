package com.xkcoding.rbac.security.controller;

import com.xkcoding.rbac.security.common.ApiResponse;
import com.xkcoding.rbac.security.common.PageResult;
import com.xkcoding.rbac.security.service.MonitorService;
import com.xkcoding.rbac.security.vo.OnlineUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 监控 Controller，在线用户，手动踢出用户等功能
 * </p>
 *
 * @package: com.xkcoding.rbac.security.controller
 * @description: 监控 Controller，在线用户，手动踢出用户等功能
 * @author: yangkai.shen
 * @date: Created in 2018-12-11 20:55
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@RestController
@RequestMapping("/api/monitor")
public class MonitorController {
    @Autowired
    private MonitorService monitorService;

    /**
     * 在线用户列表
     *
     * @param page 当前页码
     * @param size 每页条数
     */
    @PostMapping("/online/user/{page}/{size}")
    public ApiResponse onlineUser(@PathVariable Integer page, @PathVariable Integer size) {
        PageResult<OnlineUser> pageResult = monitorService.onlineUser(page, size);
        return ApiResponse.ofSuccess(pageResult);
    }

}
