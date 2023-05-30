package com.zetyun.tiger.datapublisher.service;

import com.alibaba.fastjson.JSONObject;
import com.zetyun.tiger.datapublisher.bean.ResponseData;

/**
 * Created by Smexy on 2023
 * <p>
 *  如果需求需要返回数据的格式为{}, 使用JSONObject 或 Map 封装数据。
 *  如果需求需要返回的数据的格式是 [], 使用JSONArray 或 List 封装数据。
 * <p>
 *  准备8个方法，返回指定格式的数据。
 * <p>
 *
 */
public interface DIService
{

    //查询Mysql中某天分钟级别的下单和退单数
    ResponseData queryOrderStatsByDate(String dt);

    //从redis中查询各手机的访问量
    ResponseData queryVisitStatsTop5();

    //从ck中查询各手机当日的销售额
    ResponseData querySalesAmountToday();

    //从ck统计每个手机品牌在昨日和今日的总销售额
    ResponseData querySalesAmount();

    //从ES中查询各来源数
    JSONObject querySourceData();

    //从ES中查询行业信息
    JSONObject queryIndustryStats();

    //从hbase中查询各省份今日的订单数和销售额
    JSONObject queryProvinceStatToday();

    //从hbase中查询各省份的目标完成进度
    JSONObject queryProviceCompletion(String city);

    //接收日志存入Kafka
    void saveLogToKafka(String log);
}
