package com.zetyun.tiger.datapublisher.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zetyun.tiger.datapublisher.bean.OrderStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Smexy on 2023
 */
@Mapper
@DS("mysql")
public interface MysqlMapper
{
    /*
            查询Mysql中某天分钟级别的下单和退单数
                返回的结果有3列，分别为分钟，总计的退单数和下单数
     */
    @Select("SELECT" +
        " DATE_FORMAT(dt,'%H:%i') `time`," +
        " SUM(`orderCount`) orderCount," +
        " SUM(`refundCount`) refundCount" +
        " FROM `di`" +
        " WHERE DATE(dt) = #{dt} " +
        " GROUP BY DATE_FORMAT(dt,'%H:%i') " +
        " ORDER BY `time` ")
    List<OrderStats> queryOrderStatsByDate(@Param("dt") String dt);


}
