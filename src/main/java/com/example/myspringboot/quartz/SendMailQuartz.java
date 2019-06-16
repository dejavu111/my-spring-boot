package com.example.myspringboot.quartz;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/6/15 16:00
 * @description：
 * @modified By：
 * @version: $
 */
@Component
@Configuration
@EnableScheduling
public class SendMailQuartz {
    // 日志对象
    private static final Logger logger = LogManager.getLogger(SendMailQuartz.class);

    // 每5秒钟执行一次
    @Scheduled(cron = "*/5 * * * * *")
    public void reportCurrentByCron() {
        logger.info("定时器运行了！！！");
    }
}
