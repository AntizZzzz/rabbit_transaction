package com.xjw.callback;

import com.xjw.entity.pojo.MessageRecord;

/**
 * 消息回调，有具体的生产者处理
 *
 * @author xiejianwei
 */
public interface MessageRecordCallback {

    /**
     * 回调确认是否需要提交消息
     * false则删除消息，true提交消息到MQ
     *
     * @param messageRecord
     * @return
     */
    boolean confirm(MessageRecord messageRecord);

}
