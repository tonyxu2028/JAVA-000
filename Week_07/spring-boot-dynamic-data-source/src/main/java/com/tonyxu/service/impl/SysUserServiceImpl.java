package com.tonyxu.service.impl;

import com.tonyxu.datasource.CurDataSource;
import com.tonyxu.datasource.DataSourceNames;
import com.tonyxu.entity.SysUser;
import com.tonyxu.mapper.SysUserMapper;
import com.tonyxu.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author tonyxu
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser findUserByFirstDb(long id) {
        return this.baseMapper.selectById(id);
    }

    @CurDataSource(name = DataSourceNames.SECOND)
    @Override
    public SysUser findUserBySecondDb(long id) {
        return this.baseMapper.selectById(id);
    }

}
