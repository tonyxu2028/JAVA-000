package com.tonyxu.business.client;

import com.tonyxu.business.api.Order;
import com.tonyxu.business.api.OrderService;
import com.tonyxu.business.api.User;
import com.tonyxu.business.api.UserService;
import com.tonyxu.rpcfx.core.client.RpcfxProxyStub;

/**
 * @author Tonyxu
 */
public class RpcfxClientApplication {

    private static final String SERVER =  "http://localhost:8080/";

    public static void main(String[] args) {
        UserService userService = RpcfxProxyStub.create(UserService.class, SERVER);
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

        OrderService orderService = RpcfxProxyStub.create(OrderService.class, SERVER);
        Order order = orderService.findOrderById(1992129);
        System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
    }

}
