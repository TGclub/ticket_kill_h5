package com.wizz.seckill.Mapper;

import com.wizz.seckill.Model.actInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface actInfoMapper {

    @Select("select * from actinfo where actid = #{actid}")
    actInfo getActInfoById(@Param("actid") int  actid);

    @Insert("INSERT INTO actinfo (imgurl, name, des, textdetail, ownerid, actid, theme) VALUES(#{imgurl},#{name},#{des},#{textdetail},#{ownerid}, #{actid}, #{theme})")
    @Options(useGeneratedKeys = true,keyProperty = "actid",keyColumn = "actid")
    int addActInfo(actInfo user);

    @Delete("delete from actinfo where actid = #{actid}")
    int delActInfo(@Param("actid") int actid);
}
