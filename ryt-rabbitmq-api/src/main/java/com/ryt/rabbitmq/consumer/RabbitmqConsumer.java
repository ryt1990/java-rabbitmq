package com.ryt.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


/**
 * @author renyutao
 * @date 2019/7/20 23:35
 */
@Component
public class RabbitmqConsumer {
    private static final Logger logger= LoggerFactory.getLogger(RabbitmqConsumer.class);

    @RabbitHandler
    @RabbitListener(queues = "${rabbitmq.queue-name}")
    public void receiveRabbitmqMsg(Message msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        String funcName="接收到RabbitmqMsg";
        logger.info("{}:{}",funcName,msg);
        try {

        }catch(Exception e) {

        }finally {

        }
    }
}
