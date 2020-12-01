package com.xjw.controller;

import com.xjw.entity.pojo.Order;
import com.xjw.entity.vo.R;
import com.xjw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 订单接口管理
 *
 * @author xiejianwei
 */
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @Autowired
    public OrderService orderService;

    @PostMapping("/start")
    public R page(@RequestBody String productName) {
        Order order = new Order();
        order.setAmount(BigDecimal.valueOf(5000));
        order.setProductName(productName);
        order.setOrderId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        orderService.start(order);
        return R.success();
    }
}
