package com.tonyxu.business.api;

/**
 * Created on 2020/12/22.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public interface UserService {

    /**
     * 根据ID获取用户接口
     * @param id
     * @return
     */
    User findById(Integer id);
}
