package com.wizz.seckill.Mapper;

import com.wizz.seckill.Model.reqInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "infomap")
public interface reqInfoMapper {
    @Insert("INSERT IGNORE INTO `REQTBL` ( PHONENUM , STUID, STUNAME) VALUES ( #{phoneNum}, #{stuId}, #{stuName})")
    void updateInfo(@Param("phoneNum")String phoneNum,
                    @Param("stuId")String stuId, @Param("stuName")String stuName);

    @Select("SELECT COUNT(1) FROM REQTBL")
    Long getReqSum();

    @Select("SELECT * FROM REQTBL LIMIT #{tickets}")
    List<reqInfo> getHeadK(@Param("tickets") int tickets);

    @Select("select * into outfile #{fileUrl} from REQTBL")
    void backUp(@Param("fileUrl") String fileUrl);

    @Delete("delete from REQTBL where 1;")
    void clear();
}
