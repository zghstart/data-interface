package com.zetyun.tiger.datamock.mapper;

import com.zetyun.tiger.datamock.bean.ProvinceStat;
import com.baomidou.dynamic.datasource.annotation.DS;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
@DS("hbase")
public interface HBaseMapper {
    @Update({"CREATE TABLE IF NOT EXISTS TIGER.di (saletime TIME NOT NULL,brand VARCHAR NOT NULL,province VARCHAR ,orderCount INTEGER,saleamount DECIMAL(16,2)CONSTRAINT my_pk PRIMARY KEY (saletime, brand))COLUMN_ENCODED_BYTES = 0"})
    void createTable();

    @Insert({"upsert into TIGER.di values(#{st},#{brand},#{prov},#{oc},#{sm})"})
    void insertData(@Param("st") String saleTime,
                    @Param("brand") String brand,
                    @Param("prov") String province,
                    @Param("oc") Integer orderCount,
                    @Param("sm") BigDecimal saleAmount);

    @Select({"select  province , sum(ordercount) totalOrderCount , sum(saleamount) saleAmount    from di     where substr(to_char(saletime),1,10) = #{date}     group by province"})
    List<ProvinceStat> queryProvinceStatToday(String date);
}
