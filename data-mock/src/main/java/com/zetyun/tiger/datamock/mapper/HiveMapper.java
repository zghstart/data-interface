package com.zetyun.tiger.datamock.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

//@Component
public interface HiveMapper {
    @Insert({"create table if not exists di( province string, city string, `percent` int) partitioned by (dt string)"})
    void createTable();

    @Insert({"insert into table di partition(dt=#{dt}) values ${sql}"})
    void insertData(@Param("dt") String dt, @Param("sql") String valuesSql);
}