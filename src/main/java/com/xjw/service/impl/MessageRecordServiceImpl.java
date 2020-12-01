package com.xjw.service.impl;

import com.alibaba.fastjson.JSON;
import com.xjw.config.constant.RabbitmqConstant;
import com.xjw.entity.pojo.MessageRecord;
import com.xjw.mapper.MessageRecordMapper;
import com.xjw.service.MessageRecordService;
import com.xjw.service.RabbitmqService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiejianwei
 */
@Service
public class MessageRecordServiceImpl implements MessageRecordService {

    @Autowired
    public MessageRecordMapper messageRecordMapper;

    @Autowired
    public RabbitmqService rabbitmqService;

    @Override
    public boolean preCommit(MessageRecord messageRecord) {
        return messageRecordMapper.insert(messageRecord);
    }

    @Override
    public boolean commit(String messageId, boolean commitFlag) {
        /**
         * 不提交则代表回滚
         */
        if (!commitFlag) {
            messageRecordMapper.delete(messageId);
            return true;
        }
        // 提交消息到MQ
        MessageRecord messageRecord = messageRecordMapper.find(messageId);
        /**
         * 发送MQ消息
         * 将唯一消息ID设置给CorrelationData
         * 回调时可以用这个ID查找到数据对应的消息记录
         */
        rabbitmqService.sendMessage(RabbitmqConstant.ORDER_EXCHANGE, RabbitmqConstant.ORDER_ROUTING_KEY, JSON.toJSONString(messageRecord), new CorrelationData(messageRecord.getMessageId()));
        return true;
    }

    @Override
    public void update(String messageId) {
        messageRecordMapper.update(messageId);
    }

    @Override
    public MessageRecord find(String messageId) {
        return messageRecordMapper.find(messageId);
    }

    @Override
    public List<MessageRecord> findAll(int status) {
        return messageRecordMapper.findAll(status);
    }
}
