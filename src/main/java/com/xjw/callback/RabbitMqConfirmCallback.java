package com.xjw.callback;

import com.alibaba.fastjson.JSON;
import com.xjw.config.constant.RabbitmqConstant;
import com.xjw.entity.pojo.MessageRecord;
import com.xjw.service.MessageRecordService;
import com.xjw.service.RabbitmqService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiejianwei
 */
@Component
public class RabbitMqConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private MessageRecordService messageRecordService;

    @Autowired
    public RabbitmqService rabbitmqService;

    /**
     * @param correlationData 相关配置信息
     * @param ack             交换机是否成功收到消息
     * @param cause           错误信息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        /**
         * 这个就是我们发送消息设置的messageId
         */
        String messageId = correlationData.getId();
        // 未发送成功
        if (!ack) {
            MessageRecord messageRecord = messageRecordService.find(messageId);
            if (null != messageRecord) {
                // 重发
                rabbitmqService.sendMessage(RabbitmqConstant.ORDER_EXCHANGE, RabbitmqConstant.ORDER_ROUTING_KEY, JSON.toJSONString(messageRecord), new CorrelationData(messageRecord.getMessageId()));
            }
        } else {
            // 修改消息状态为成功
            messageRecordService.update(messageId);
        }
    }
}
