package com.example.myspringboot.quartz;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TestTask {
    private static final Logger logger = LogManager.getLogger(TestTask.class);

    public void run() {
        logger.info("定时器运行了");
    }
}
