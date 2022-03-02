package com.example.daily.mapper;

import com.example.daily.entity.Daily;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/5  11:23
 * @Version: 1.0
 * @Modified: By:
 */
@Mapper
public interface DailyMapper {
    /**
     * 根据id查询日记
     */
    @Select("SELECT * FROM T_DAILY WHERE dailyId = #{dailyId}")
    Daily findById(@Param("dailyId") Integer dailyId);

    /**
     * 新增日记
     */
    @Insert("INSERT INTO T_Daily(dailyTitle, dailyDate, byPerson,updateDate,dailyContent) VALUES(#{dailyTitle}, #{dailyDate}, #{byPerson}, #{updateDate},#{dailyContent})")
    int insert(@Param("dailyTitle") String dailyTitle, @Param("dailyDate") Date dailyDate, @Param("byPerson") String byPerson, @Param("updateDate") Date updateDate, @Param("dailyContent") String dailyContent);

    /**
     * 删除日记
     */
    @Delete("delete from T_Daily where id = #{id}")
    int deleteDaily(@Param("id") Integer id);


}

