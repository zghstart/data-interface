package com.zetyun.tiger.datapublisher.controller;

import com.zetyun.tiger.datapublisher.service.DIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Smexy on 2023
 *
 *  数据接口发布的一般步骤:
 *          ①分析需求
 *                  通过阅读需求文档。
 *                      请求的url
 *                      期望返回的数据格式。
 *                              {}:  最终返回一个Map集合或JSONObject。
 *                                      自定义Bean
 *                              []:  最终返回一个List集合或JSONArray
 *
 *
 *          ②联系数据
 *                  当前需求如何从数据库查询得到。
 *                      数据的请求连接(地址，端口，库)
 *                      编写sql或NoSQLApi去查询相关的数据
 *          ③如果返回的数据字段较多，可以使用Bean进行封装
 *          ④按照MVC的范式开发
 *                  编写Service接口
 *                  编写Dao
 *                  编写Service的实现
 *                  测试Service的实现
 *                  编写控制器
 *
 */
@RestController
public class DIController
{
    @Autowired
    private DIService diService;

    @RequestMapping(value = "/queryOrderStats")
    public Object queryOrderStatsByDate(String dt){

        if (StringUtils.hasText(dt)){
            return diService.queryOrderStatsByDate(dt);
        }else{
            return "notok";
        }

    }

    @RequestMapping(value = "/queryVisitStatsTop5")
    public Object queryVisitStatsTop5(){

        return diService.queryVisitStatsTop5();

    }

    @RequestMapping(value = "/querySalesAmountToday")
    public Object querySalesAmountToday(){

        return diService.querySalesAmountToday();

    }

    @RequestMapping(value = "/querySalesAmount")
    public Object querySalesAmount(){

        return diService.querySalesAmount();

    }

    @RequestMapping(value = "/querySourceData")
    public Object querySourceData(){

        return diService.querySourceData();

    }

    @RequestMapping(value = "/queryIndustryStats")
    public Object queryIndustryStats(){

        return diService.queryIndustryStats();

    }

    @RequestMapping(value = "/queryProvinceStatToday")
    public Object queryProvinceStatToday(){

        return diService.queryProvinceStatToday();

    }

    @RequestMapping(value = "/queryProviceCompletion")
    public Object queryProviceCompletion(String name){

        return diService.queryProviceCompletion(name);

    }

    //编写方法接收数据
    @RequestMapping(value = "/app")
    public Object receiveLogData(String logStr){

        diService.saveLogToKafka(logStr);
         return "ok";
    }
}
