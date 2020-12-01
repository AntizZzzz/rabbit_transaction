package com.xjw.config;

import com.xjw.config.constant.RabbitmqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiejianwei
 * @ClassName BusinessOrderRabbitMqConfig
 */
@Configuration
public class OrderRabbitMqConfig {

    /**
     * 初始化队列
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(RabbitmqConstant.ORDER_QUEUE, true);
    }

    /**
     * 初始化交换机
     *
     * @return
     */
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(RabbitmqConstant.ORDER_EXCHANGE, true, false);
    }

    /**
     * 队列通过路由键绑定到交换机
     *
     * @return
     */
    @Bean
    public Binding bind() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(RabbitmqConstant.ORDER_ROUTING_KEY);
    }
}
