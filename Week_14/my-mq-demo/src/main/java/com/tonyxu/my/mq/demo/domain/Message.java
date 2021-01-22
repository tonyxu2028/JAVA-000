package com.tonyxu.my.mq.demo.domain;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;

/**
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Data
@ToString
public class Message<T> {

    private HashMap<String,Object> headers;

    private T body;
}