package com.xkcoding.orm.mybatis.plus.task;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

        @Override
        public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
              scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
        }
}


