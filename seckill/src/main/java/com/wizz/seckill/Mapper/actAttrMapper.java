package com.wizz.seckill.Mapper;

import com.wizz.seckill.Model.actAtr;
import com.wizz.seckill.Model.actInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "actamap")
public interface actAttrMapper {
    @Select("select * from actattr where actid = #{actid}")
    actAtr getActAttrById(@Param("actid") int  actid);

    @Insert("INSERT INTO actattr (begtime, endtime, seckilltime, tickets, ownerid) VALUES(#{begtime},#{endtime},#{seckilltime},#{tickets},#{ownerid})")
    @Options(useGeneratedKeys = true, keyProperty = "actid",keyColumn = "actid")
    int addActAttr(actAtr user);

    @Delete("delete from actattr where actid = #{actid}")
    int delActAttr(@Param("actid") int actid);

    @Select("SELECT MAX(actid) from actattr")
    Integer recent();

}
