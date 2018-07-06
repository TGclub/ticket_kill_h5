package com.wizz.seckill.Service;

import com.wizz.seckill.Model.SysUser;

/**
 * @author pjmike
 * @create 2018-05-19 14:57
 */
public interface SysUserService {
    SysUser findUserById(Integer id);

    SysUser findUserByName(String username);

    SysUser addSysUser(SysUser user);
}
