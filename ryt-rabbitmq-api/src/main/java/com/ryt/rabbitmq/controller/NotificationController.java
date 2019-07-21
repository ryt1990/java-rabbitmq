package com.ryt.rabbitmq.controller;

import com.ryt.rabbitmq.model.TestModel;
import com.ryt.rabbitmq.producer.RabbitmqProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author renyutao
 * @date 2019/7/21 16:53
 */
@RestController
@RequestMapping("notification")
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    
    @Autowired
    RabbitmqProducer producer;

    @Value("${rabbitmq.routing-key}")
    String routingKey;
    
    @RequestMapping("send")
    public void sendMsg(){
        TestModel testModel =new TestModel();
        testModel.setName("fxx");
        testModel.setAge(18);
        testModel.setGender("girl");
        try {
            producer.sendNotification(routingKey,testModel);
        }catch (Exception e){
            logger.error("系统异常："+e.toString());
        }
    }
}
