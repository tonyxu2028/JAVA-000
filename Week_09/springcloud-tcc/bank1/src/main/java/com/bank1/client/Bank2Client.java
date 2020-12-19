package com.bank1.client;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="bank2-server",fallback=Bank2ClientFallback.class)
@Service
public interface Bank2Client {


    @GetMapping("/bank2/transfer")
    @Hmily
    Boolean transfer(@RequestParam("cardNumber")  String cardNumber,@RequestParam("money") Integer money);

}
