package com.bank1.excepiton;

public class FeignClientException extends RuntimeException {

    public FeignClientException(String msg){
        super(msg);
    }

}
