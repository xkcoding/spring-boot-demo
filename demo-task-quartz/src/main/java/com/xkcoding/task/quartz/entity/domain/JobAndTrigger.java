package com.xkcoding.task.quartz.entity.domain;

import lombok.Data;

import java.math.BigInteger;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-26 15:05
 */
@Data
public class JobAndTrigger {
    /**
     * 定时任务名称
     */
    private String jobName;
    /**
     * 定时任务组
     */
    private String jobGroup;
    /**
     * 定时任务全类名
     */
    private String jobClassName;
    /**
     * 触发器名称
     */
    private String triggerName;
    /**
     * 触发器组
     */
    private String triggerGroup;
    /**
     * 重复间隔
     */
    private BigInteger repeatInterval;
    /**
     * 触发次数
     */
    private BigInteger timesTriggered;
    /**
     * cron 表达式
     */
    private String cronExpression;
    /**
     * 时区
     */
    private String timeZoneId;
    /**
     * 定时任务状态
     */
    private String triggerState;
}
