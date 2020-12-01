package com.xjw.service.impl;

import com.xjw.entity.pojo.MessageRecord;
import com.xjw.entity.pojo.Order;
import com.xjw.service.MessageRecordService;
import com.xjw.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author xiejianwei
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    public MessageRecordService messageRecordService;

    /**
     * 模拟发起一个简单的订单
     *
     * @param order
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean start(Order order) {
        //触发保存本地消息表
        MessageRecord messageRecord = new MessageRecord(order.getOrderId(), 1);
        messageRecordService.preCommit(messageRecord);
        log.info("这里可以做本地业务操作");
        log.info("下单中，请稍等-----");
        log.info("恭喜您，下单成功，订单号：{}", order.getOrderId());
        // 操作本地事务成功则commit 消息，如果处理本地事务异常，则会有定时任务回调
        messageRecordService.commit(messageRecord.getMessageId(), true);
        return true;
    }
}
