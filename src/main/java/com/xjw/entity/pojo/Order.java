package com.xjw.entity.pojo;

import com.xjw.entity.dto.SerializableDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author xiejianwei
 */
@Getter
@Setter
public class Order extends SerializableDto {

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 做简单的例子就不关联业务ID了
     */
    private String productName;
}
