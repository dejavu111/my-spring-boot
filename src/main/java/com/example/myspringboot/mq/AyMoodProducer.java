package com.example.myspringboot.mq;

import com.example.myspringboot.model.AyMood;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * 生产者
 * @author ：dejavu111
 * @date ：Created in 2019/7/29 16:10
 */
@Service
public class AyMoodProducer {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(Destination destination,
                            final String message) {
        jmsMessagingTemplate.convertAndSend(destination,message);
    }
    public void sendMessage(Destination destination,
                            final AyMood ayMood) {
        jmsMessagingTemplate.convertAndSend(destination,ayMood);
    }

}
