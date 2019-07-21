package com.ryt.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author renyutao
 * @date 2019/7/21 18:55
 */
@Component
public class RabbitmqProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    public  static final Logger logger = LoggerFactory.getLogger(RabbitmqProducer.class);
    @Value("${rabbitmq.exchange-key}")
    String exchangeKey;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbit(){
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }

    /**
     * 发送MQ
     * @param routingKey
     * @param obj 参数
     */
//    public void sendNotification(String routingKey, Object obj){
//        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//        this.rabbitTemplate.convertAndSend(
//                exchangeKey,
//                routingKey,
//                JSON.toJSONString(obj),
//                correlationData);
//    }
    

    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 发送MQ
     * @param routingKey
     * @param obj 参数
     */
    public void sendNotification(String routingKey, Object obj){
        this.amqpTemplate.convertAndSend(exchangeKey,routingKey,obj);
    }

    /**
     * 只确认是否正确到达 Exchange 中，针对message的provider
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            logger.error("消息投递到exchange失败, cause:{}", cause);
            return;
        }
        logger.info("消息投递到exchange成功, id: {}", correlationData.getId());
    }

    /**
     * 路由不到队列时触发回调
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error("消息无法路由到指定队列, message:{}, exchange:{}, routingKey:{}", message, exchange, routingKey);
    }
}
