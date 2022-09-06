package com.xkcoding.task.quartz.job;

import cn.hutool.core.date.DateUtil;
import com.xkcoding.task.quartz.job.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

/**
 * <p>
 * Test Job
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-26 13:22
 */
@Slf4j
public class TestJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) {
        log.error("Test Job 执行时间: {}", DateUtil.now());
    }
}
