package com.tonyxu.business.api;

/**
 * Created on 2020/12/22.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public interface OrderService {

    /**
     * 根据ID获取订单接口
     * @param id
     * @return
     */
    Order findOrderById(Integer id);
}
