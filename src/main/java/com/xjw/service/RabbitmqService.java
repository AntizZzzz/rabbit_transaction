package com.xjw.service;


import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @author xiejianwei
 * @Description 发送mq消息
 */
public interface RabbitmqService {

    /**
     * 发送消息到mq(单个)
     *
     * @param exchange
     * @param routingKey
     * @param messages
     * @param correlationData
     */
    void sendMessage(String exchange, String routingKey, String messages, CorrelationData correlationData);
}
