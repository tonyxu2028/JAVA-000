package com.bank1.client;

import org.springframework.stereotype.Component;

@Component
public class Bank2ClientFallback implements Bank2Client{

    @Override
    public Boolean transfer(String cardNumber, Integer money) {
        return false;
    }
}
