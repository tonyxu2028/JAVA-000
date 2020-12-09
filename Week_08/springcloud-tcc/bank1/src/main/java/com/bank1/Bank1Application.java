package com.bank1;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.bank1.mapper")
@EnableFeignClients
public class Bank1Application {

    public static void main(String[] args) {

        SpringApplication.run(Bank1Application.class);

    }

}
