package com.xkcoding.task.xxl.job.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 手动操作 xxl-job
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-07 14:58
 */
@Slf4j
@RestController
@RequestMapping("/xxl-job")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ManualOperateController {
    private final static String baseUri = "http://127.0.0.1:18080/xxl-job-admin";
    private final static String JOB_INFO_URI = "/jobinfo";
    private final static String JOB_GROUP_URI = "/jobgroup";

    /**
     * 任务组列表，xxl-job叫做触发器列表
     */
    @GetMapping("/group")
    public String xxlJobGroup() {
        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_GROUP_URI + "/list").execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 分页任务列表
     *
     * @param page 当前页，第一页 -> 0
     * @param size 每页条数，默认10
     * @return 分页任务列表
     */
    @GetMapping("/list")
    public String xxlJobList(Integer page, Integer size) {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("start", page != null ? page : 0);
        jobInfo.put("length", size != null ? size : 10);
        jobInfo.put("jobGroup", 2);
        jobInfo.put("triggerStatus", -1);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/pageList").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动保存任务
     */
    @GetMapping("/add")
    public String xxlJobAdd() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("jobGroup", 2);
        jobInfo.put("jobCron", "0 0/1 * * * ? *");
        jobInfo.put("jobDesc", "手动添加的任务");
        jobInfo.put("author", "admin");
        jobInfo.put("executorRouteStrategy", "ROUND");
        jobInfo.put("executorHandler", "demoTask");
        jobInfo.put("executorParam", "手动添加的任务的参数");
        jobInfo.put("executorBlockStrategy", ExecutorBlockStrategyEnum.SERIAL_EXECUTION);
        jobInfo.put("glueType", GlueTypeEnum.BEAN);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/add").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动触发一次任务
     */
    @GetMapping("/trigger")
    public String xxlJobTrigger() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);
        jobInfo.put("executorParam", JSONUtil.toJsonStr(jobInfo));

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/trigger").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动删除任务
     */
    @GetMapping("/remove")
    public String xxlJobRemove() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/remove").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动停止任务
     */
    @GetMapping("/stop")
    public String xxlJobStop() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/stop").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动启动任务
     */
    @GetMapping("/start")
    public String xxlJobStart() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/start").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

}
