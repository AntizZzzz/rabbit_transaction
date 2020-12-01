package com.xjw.service;

import com.xjw.entity.pojo.Order;

/**
 * @author xiejianwei
 */
public interface OrderService {

    /**
     * 模拟发起一个订单
     *
     * @param order
     * @return
     */
    boolean start(Order order);

}
