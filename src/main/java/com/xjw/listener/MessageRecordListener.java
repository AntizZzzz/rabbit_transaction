package com.xjw.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xjw.config.constant.RabbitmqConstant;
import com.xjw.entity.pojo.MessageRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xiejianwei
 */
@Component
@RabbitListener(queues = RabbitmqConstant.ORDER_QUEUE)
@Slf4j
public class MessageRecordListener {

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    /**
     * 下游服务消费一直失败这个暂时就不弄了，也挺简单的
     * 消费的时候维护一个计数，达到五次则入库，触发报警后人工干预
     *
     * @param msg
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitHandler
    public void process(String msg, Channel channel, Message message) throws IOException {
        try {
            // 序列化消息
            MessageRecord messageRecord = JSON.parseObject(msg, MessageRecord.class);
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            /**
             * 防止重复消费
             */
            if (ops.setIfAbsent(messageRecord.getMessageId(), messageRecord.getMessageId())) {
                log.info("下单成功，执行仓储服务");
                log.info("下单成功，执行积分服务");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            //消费者处理出了问题，需要告诉队列信息消费失败
            /**
             * 拒绝确认消息:<br>
             * channel.basicNack(long deliveryTag, boolean multiple, boolean requeue) ; <br>
             * deliveryTag:该消息的index<br>
             * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。<br>
             * requeue：被拒绝的是否重新入队列 <br>
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);

            /**
             * 拒绝一条消息：<br>
             * channel.basicReject(long deliveryTag, boolean requeue);<br>
             * deliveryTag:该消息的index<br>
             * requeue：被拒绝的是否重新入队列
             */
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
