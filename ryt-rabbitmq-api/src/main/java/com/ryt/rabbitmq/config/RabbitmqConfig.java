package com.ryt.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author renyutao
 * @date 2019/7/20 22:47
 */
@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.queue-name}")
    String queueName;

    @Value("${rabbitmq.exchange-key}")
    String exchangeKey;

    @Value("${rabbitmq.routing-key}")
    String routingKey;

    @Bean(value = "${rabbitmq.queue-name}")
    public Queue initOrderCancelQueue(){
        return new Queue(queueName) ;
    }

    @Bean(value = "${rabbitmq.exchange-key}")
    public CustomExchange customExchange(){
        Map<String,Object> args=new HashMap<>();
        return new CustomExchange(exchangeKey,"topic",true,false,args);
    }

    @Bean
    public Binding binding(@Qualifier(value = "${rabbitmq.queue-name}") Queue queue,
                           @Qualifier(value = "${rabbitmq.exchange-key}") CustomExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey)
                .and(exchange.getArguments());
    }
}
