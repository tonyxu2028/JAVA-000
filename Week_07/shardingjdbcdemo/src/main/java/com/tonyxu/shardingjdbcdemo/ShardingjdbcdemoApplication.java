package com.tonyxu.shardingjdbcdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created on 2020/12/3.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SpringBootApplication
@MapperScan("com.tonyxu.shardingjdbcdemo.mapper")
public class ShardingjdbcdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcdemoApplication.class, args);
    }

}
