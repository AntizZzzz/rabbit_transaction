package com.xjw.service.impl;

import com.xjw.callback.RabbitMqConfirmCallback;
import com.xjw.service.RabbitmqService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiejianwei
 * @ClassName RabbitmqServiceImpl
 * @Description 发送mq消息
 */
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMqConfirmCallback rabbitMqConfirmCallback;

    /**
     * 发送消息到mq(单个)
     *
     * @param exchange   交换机的名称
     * @param routingKey 路由key值
     * @param messages   消息的附件消息
     */
    @Override
    public void sendMessage(String exchange, String routingKey, String messages, CorrelationData correlationData) {
        /**
         * 设置回调
         */
        rabbitTemplate.setConfirmCallback(rabbitMqConfirmCallback);
        rabbitTemplate.convertAndSend(exchange, routingKey, messages, correlationData);
    }
}
