package com.xkcoding.orm.mybatis.plus.task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PendingSchelude {

        @Scheduled(cron = "0 0/1 * * * ?")
        public void run1() {
            log.info("RunJob is running");
            for (int i = 0; i < 100; i++) {
                    i++;
            }
            log.info("RunJob is end");
        }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run2() {
        log.info("PendingJob is running");
        long start = System.currentTimeMillis() + (2 * 60 * 1000);
        while (true) {
            long end = System.currentTimeMillis();
            if(end >= start) {
                break;
            }
        }
        log.info("PendingJob is end");
    }

}
