package com.zetyun.tiger.datamock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@DS("mysql")
@Mapper
public interface MysqlMapper {
    // utf8mb4_0900_ai_ci 该字符集只在mysql8版本之后会有
//    @Update({"CREATE TABLE IF NOT EXISTS `di` (  `dt` DATETIME NOT NULL,  `orderCount` BIGINT DEFAULT NULL,  `refundCount` BIGINT DEFAULT NULL,  PRIMARY KEY (`dt`)) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"})
    @Update({"CREATE TABLE IF NOT EXISTS `di` (  `dt` DATETIME NOT NULL,  `orderCount` BIGINT DEFAULT NULL,  `refundCount` BIGINT DEFAULT NULL,  PRIMARY KEY (`dt`)) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci"})
    void createTable();

    @Insert({" INSERT INTO di VALUES(#{date},#{oc},#{rc}) ON DUPLICATE KEY UPDATE ordercount=ordercount + VALUES(ordercount), refundCount=refundCount + VALUES(refundCount)"})
    void insertData(@Param("date") String date, @Param("oc") Integer oc, @Param("rc") Integer rc);
}
