package com.tonyxu.business.config;

import com.tonyxu.business.DemoServerResolver;
import com.tonyxu.business.api.OrderService;
import com.tonyxu.business.api.UserService;
import com.tonyxu.business.service.OrderServiceImpl;
import com.tonyxu.business.service.UserServiceImpl;
import com.tonyxu.rpcfx.core.server.ReflectionInvoker;
import com.tonyxu.rpcfx.core.server.RpcfxResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcfxServerConfig {

    @Bean
    public RpcfxResolver demoServerResolver() {
        return new DemoServerResolver();
    }

    @Bean
    public ReflectionInvoker reflectionInvoker(RpcfxResolver demoServerResolver) {
        return new ReflectionInvoker(demoServerResolver);
    }

    @Bean(name = "learn.rpc.custom.demo.api.service.UserService")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean(name = "learn.rpc.custom.demo.api.service.IOrderService")
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

}
