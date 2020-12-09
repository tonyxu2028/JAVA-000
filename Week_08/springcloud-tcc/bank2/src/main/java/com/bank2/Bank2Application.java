package com.bank2;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@MapperScan("com.bank2.mapper")
@EnableFeignClients
public class Bank2Application {

    public static void main(String[] args) {

        SpringApplication.run(Bank2Application.class);

    }

}
