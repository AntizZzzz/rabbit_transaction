package com.xjw.service;

import com.xjw.entity.pojo.MessageRecord;

import java.util.List;

/**
 * @author xiejianwei
 */
public interface MessageRecordService {

    /**
     * 保存一条消息记录
     *
     * @param messageRecord
     * @return
     */
    boolean preCommit(MessageRecord messageRecord);

    /**
     * 提交一条消息记录
     *
     * @param messageId
     * @param commitFlag 如果为false则回滚消息，删除
     * @return
     */
    boolean commit(String messageId, boolean commitFlag);

    /**
     * 修改
     *
     * @param messageId
     */
    void update(String messageId);

    /**
     * 根据消息ID查找
     *
     * @param messageId
     * @return
     */
    MessageRecord find(String messageId);

    /**
     * 根据状态查找
     *
     * @param status
     * @return
     */
    List<MessageRecord> findAll(int status);
}
