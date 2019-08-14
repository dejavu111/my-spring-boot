package com.example.myspringboot.service.impl;

import com.example.myspringboot.model.AyMood;
import com.example.myspringboot.mq.AyMoodProducer;
import com.example.myspringboot.repository.AyMoodRepository;
import com.example.myspringboot.service.AyMoodService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;


/**
 * @author ：dejavu111
 * @date ：Created in 2019/7/29 15:49
 */
@Service
public class AyMoodServiceImpl implements AyMoodService {
    @Resource
    private AyMoodRepository ayMoodRepository;
    @Override
    public AyMood save(AyMood ayMood) {
        return ayMoodRepository.save(ayMood);
    }

    // 队列
    private static Destination destination = new ActiveMQQueue("ay.queue.asyn.save");
    @Resource
    private AyMoodProducer ayMoodProducer;
    @Override
    public String asynSave(AyMood ayMood) {
        // 往队列ay.queue.asyn.save推送消息，消息内容为说说实体
        ayMoodProducer.sendMessage(destination,ayMood);
        return "success";
    }
}
