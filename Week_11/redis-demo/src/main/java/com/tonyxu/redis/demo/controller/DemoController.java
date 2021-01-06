package com.tonyxu.redis.demo.controller;

import com.tonyxu.redis.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tony.xu
 * @date 2021-01-05 18:11
 */
@RestController
@EnableAutoConfiguration
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("redis/demo/flash")
    public String flashSale() {
        return demoService.decreaseInventory();
    }
}
