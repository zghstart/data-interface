package com.zetyun.tiger.datamock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
@DS("ck")
public interface CKMapper {
    @Update({"create table if not exists di(createTime DateTime,brand String,saleAmount Decimal64(2),dt String )engine = SummingMergeTree(saleAmount) partition by (dt) order by (brand);"})
    void createTable();

    @Insert({"insert into di values(#{dateTime},#{brand},#{sm},#{dt})"})
    void insertData(@Param("dateTime") String dateTime,
                    @Param("brand") String brand,
                    @Param("sm") BigDecimal saleAmount,
                    @Param("dt") String dt);
}
