package com.zetyun.tiger.datapublisher.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zetyun.tiger.datapublisher.bean.ProvinceOrderStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.elasticsearch.client.license.LicensesStatus;

import java.util.List;

/**
 * Created by Smexy on 2023
 */
@DS("hbase")
@Mapper
public interface HBaseMapper
{
    @Select("select province name, sum(ORDERCOUNT) \"value\" , sum(SALEAMOUNT) sizeValue  " +
        "    from di " +
        "    where substr(to_char(SALETIME),1,10) = #{dt} " +
        "    group by PROVINCE")
    List<ProvinceOrderStats> queryProvinceOrderStats(@Param("dt") String dt);

    @Select("select sum(SALEAMOUNT) " +
        "    from di " +
        "    where substr(to_char(SALETIME),1,10) = #{dt} and " +
        "    PROVINCE = #{provinceName}")
    Double queryProvinceAmountToday(@Param("dt") String dt,@Param("provinceName") String name);
}
