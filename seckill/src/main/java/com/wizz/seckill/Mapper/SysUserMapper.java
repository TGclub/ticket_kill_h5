package com.wizz.seckill.Mapper;

import com.wizz.seckill.Model.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author pjmike
 * @create 2018-05-19 14:59
 */
@Mapper
@Component
public interface SysUserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    SysUser findUserById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    SysUser findUserByName(@Param("username") String username);

    @Insert("INSERT INTO user(username,password) VALUES(#{username},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insertUser(SysUser user);
}
