package com.tonyxu.service;

import org.springframework.stereotype.Component;

/**
 * Created on 2020/11/17.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Component(value = "userService")
public class UserServiceImpl implements UserService{

    public void printUserName() {
        System.out.println("User_Name :::: Tonyxu");
    }

}
