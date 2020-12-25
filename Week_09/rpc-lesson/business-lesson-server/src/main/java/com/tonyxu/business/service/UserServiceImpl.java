package com.tonyxu.business.service;


import com.tonyxu.business.api.User;
import com.tonyxu.business.api.UserService;

public class UserServiceImpl implements UserService {
    /**
     * 根据ID获取用户接口
     *
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        return new User(id, "Tonyxu" + System.currentTimeMillis());
    }
}
