package com.bank2.controller;


import com.bank2.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank2")
@RestController
public class Bank2Controller {

    @Autowired
    private AccountInfoService accountInfoService;

    @GetMapping("/transfer")
    public Boolean transfer(@RequestParam("cardNumber") String cardNumber, @RequestParam("money") Integer money) {
        accountInfoService.addAccountBalance(cardNumber, money);
        return true;
    }
}
