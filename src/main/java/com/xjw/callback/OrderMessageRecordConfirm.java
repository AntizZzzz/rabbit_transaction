package com.xjw.callback;

import com.xjw.entity.pojo.MessageRecord;
import org.springframework.stereotype.Component;

/**
 * 根据具体的业务，判断是否需要提交或者回滚消息
 *
 * @author xiejianwei
 */
@Component
public class OrderMessageRecordConfirm implements MessageRecordCallback {

    @Override
    public boolean confirm(MessageRecord messageRecord) {
        String messageId = messageRecord.getMessageId();
        /**
         * 根据具体的业务，判断是否需要提交或者回滚消息
         */
        if ("1212321".equals(messageId)) {
            return true;
        }
        return false;
    }
}
