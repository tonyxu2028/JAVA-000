package com.tonyxu.business.service;


import com.tonyxu.business.api.Order;
import com.tonyxu.business.api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * 根据ID获取订单接口
     *
     * @param id
     * @return
     */
    @Override
    public Order findOrderById(Integer id) {
        logger.info("OrderServiceImpl收到findById: {}.", id);
        Order order = new Order(new Integer(1),"Tonyxu",new BigDecimal(10000000));
        logger.info("HelloServiceImpl返回: {}.", order.toString());
        return order;
    }
}
