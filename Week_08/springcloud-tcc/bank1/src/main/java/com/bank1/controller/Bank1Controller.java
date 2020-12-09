package com.bank1.controller;


import com.bank1.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank1")
@RestController
public class Bank1Controller {

    @Autowired
    private AccountInfoService accountInfoService;

    @GetMapping("/transfer")
    public Boolean transfer(@RequestParam("cardNumber") String cardNumber, @RequestParam("money") Integer money) {
        accountInfoService.updateAccountBalance(cardNumber, money);
        return true;
    }
}
