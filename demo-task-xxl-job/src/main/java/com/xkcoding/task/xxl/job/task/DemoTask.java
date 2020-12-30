package com.xkcoding.task.xxl.job.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 测试定时任务
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-07 10:15
 */
@Slf4j
@Component
@JobHandler("demoTask")
public class DemoTask extends IJobHandler {

    /**
     * execute handler, invoked when executor receives a scheduling request
     *
     * @param param 定时任务参数
     * @return 执行状态
     * @throws Exception 任务异常
     */
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        // 可以动态获取传递过来的参数，根据参数不同，当前调度的任务不同
        log.info("【param】= {}", param);
        XxlJobLogger.log("demo task run at : {}", DateUtil.now());
        return RandomUtil.randomInt(1, 11) % 2 == 0 ? SUCCESS : FAIL;
    }
}
