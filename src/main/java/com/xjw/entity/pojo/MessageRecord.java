package com.xjw.entity.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * @author xiejianwei
 */
@Getter
@Setter
public class MessageRecord {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 业务数据ID
     */
    private String businessId;
    /**
     * 业务类型
     */
    private int businessType;
    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 重试次数
     */
    private int retriesNumber;
    /**
     * 消息状态 (0.失败，1成功)
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createTime;

    public MessageRecord() {
    }

    public MessageRecord(String businessId, int businessType) {
        this.businessId = businessId;
        this.businessType = businessType;
        this.messageId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        this.retriesNumber = 0;
        this.createTime = new Date();
        this.status = 0;
    }
}
