package com.xjw.task;

import com.xjw.callback.MessageRecordCallback;
import com.xjw.entity.pojo.MessageRecord;
import com.xjw.service.MessageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiejianwei
 */
@Component
@EnableScheduling
public class MessageRecordConfirmTask {

    @Autowired
    public MessageRecordService messageRecordService;

    @Autowired
    public MessageRecordCallback messageRecordCallback;

    /**
     * 每隔5分钟轮询消息表
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void confirm() {
        // 查询所有状态等于0(未提交的状态)
        List<MessageRecord> all = messageRecordService.findAll(0);
        if (null != all && all.size() > 0) {
            all.forEach(messageRecord -> {
                boolean confirm = messageRecordCallback.confirm(messageRecord);
                // 根据回调结果执行提交或者回滚
                messageRecordService.commit(messageRecord.getMessageId(), confirm);
            });
        }
    }
}
