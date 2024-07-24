package com.myself.demo.original.deletefile;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

public class DeleteExpiredFile {
    private static final Logger logger  = LoggerFactory.getLogger(DeleteExpiredFile.class);
    private static final String ROOT_DIR_HISTORY_PATH = System.getProperty("work.dir");

    public void deleteExpiredFileTask() {
        File file = new File(ROOT_DIR_HISTORY_PATH+"logs/spring-boot-demo-logback");
        deleteExpiredFile(file);
    }

    private void deleteExpiredFile(File file) {
        if (!file.exists()) return;
        if (!file.isDirectory()) {
            determineExpiredFile(file);
        } else {
            for (File f : file.listFiles()) {
                deleteExpiredFile(f);
            }
        }
    }

    @Test
    private void determineExpiredFile(File file) {
        long lastModifiedTime = file.lastModified();
        long currentTime = new Date().getTime();
        long timeInterval =1 * 60 * 1000;
        if (currentTime - lastModifiedTime > timeInterval) {
            file.delete();
        }
    }

    @Test
    public   void  getLogsDel(){
        for (int i = 0; i <100 ; i++) {
            logger.info("我叫广汽本田");
        }

    }


}

