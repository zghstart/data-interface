package com.zetyun.tiger.datapublisher.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.zetyun.tiger.datapublisher.bean.*;
import com.zetyun.tiger.datapublisher.dao.CKMapper;
import com.zetyun.tiger.datapublisher.dao.ESDao;
import com.zetyun.tiger.datapublisher.dao.HBaseMapper;
import com.zetyun.tiger.datapublisher.dao.MysqlMapper;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Smexy on 2023
 */
@Service
public class DIServiceImpl implements  DIService
{
    //引入Dao
    @Autowired
    private MysqlMapper mysqlMapper;

    //案例一
    @Override
    public ResponseData queryOrderStatsByDate(String dt) {

        //查询到的数据
        List<OrderStats> orderStats = mysqlMapper.queryOrderStatsByDate(dt);

        /*//声明最终返回的数据对象
        JSONObject result = new JSONObject();
        JSONObject dataJO = new JSONObject();
        JSONObject orderJO = new JSONObject();
        JSONObject refundJO = new JSONObject();
        List<String> categories = new ArrayList<>();
        List<Integer> orderCountData = new ArrayList<>();
        List<Integer> refundCountData = new ArrayList<>();
        List<JSONObject> seriesData = new ArrayList<>();

        //按照规定的格式返回数据
        //封装series部分
        for (OrderStats orderStat : orderStats) {
            categories.add(orderStat.getTime());
            orderCountData.add(orderStat.getOrderCount());
            refundCountData.add(orderStat.getRefundCount());
        }

        orderJO.put("name","新增订单数");
        orderJO.put("data",orderCountData);

        refundJO.put("name","退单数");
        refundJO.put("data",refundCountData);

        seriesData.add(orderJO);
        seriesData.add(refundJO);

        //封装data
        dataJO.put("categories",categories);
        dataJO.put("series",seriesData);

        //封装最外层的result
        result.put("status",0);
        result.put("msg","");
        result.put("data",dataJO);*/

        List<String> categories = new ArrayList<>();
        List<Integer> orderCountData = new ArrayList<>();
        List<Integer> refundCountData = new ArrayList<>();
        //封装series部分
        for (OrderStats orderStat : orderStats) {
            categories.add(orderStat.getTime());
            orderCountData.add(orderStat.getOrderCount());
            refundCountData.add(orderStat.getRefundCount());
        }

        //嵌套的数据
        SeriesData<Integer> orderSeriesData = new SeriesData<>("新增订单数", orderCountData);
        SeriesData<Integer> refundSeriesData = new SeriesData<>("退单数", refundCountData);

        //需要返回的数据格式
        ResponseData responseData = new ResponseData("", 0, categories, Arrays.asList(orderSeriesData, refundSeriesData));

        return responseData;
    }

    /*
        ZrevRANGE hotphone 0 4 withscores

        当环境中引入了 spring-boot-starter-data-redis 后，spring容器提供一个可以操作redis的客户端。
        只需要从容器中获取客户端，直接使用即可。

        springboot 中提供的操作数据库的客户端习惯以  数据库名字Template 作为类型。
     */

    @Autowired
    private RedisTemplate<String,String> redicClient;

    //从配置文件中读取指定的value，赋值给当前变量
    @Value("${di.redis.key}")
    private String redisKey;

    //案例二
    @Override
    public ResponseData queryVisitStatsTop5() {
        Set<ZSetOperations.TypedTuple<String>> res = redicClient.opsForZSet().reverseRangeWithScores(redisKey, 0, 4);

        //声明最终返回的数据对象
       /* JSONObject result = new JSONObject();
        JSONObject dataJO = new JSONObject();
        JSONObject brandJO = new JSONObject();
        List<String> categories = new ArrayList<>();
        List<Double> brandData = new ArrayList<>();
        List<JSONObject> seriesData = new ArrayList<>();

        //按照规定的格式返回数据
        //封装series部分
        for (ZSetOperations.TypedTuple<String> re : res) {
            categories.add(re.getValue());
            brandData.add(re.getScore());
        }

        brandJO.put("name","手机品牌");
        brandJO.put("data",brandData);

        seriesData.add(brandJO);

        //封装data
        dataJO.put("categories",categories);
        dataJO.put("series",seriesData);

        //封装最外层的result
        result.put("status",0);
        result.put("msg","");
        result.put("data",dataJO);*/


        List<String> categories = new ArrayList<>();
        List<Double> brandData = new ArrayList<>();

        //封装series部分
        for (ZSetOperations.TypedTuple<String> re : res) {
            categories.add(re.getValue());
            brandData.add(re.getScore());
        }

        SeriesData<Double> orderSeriesData = new SeriesData<>("手机品牌", brandData);
        ResponseData responseData = new ResponseData("", 0, categories, Arrays.asList(orderSeriesData));

        return responseData;
    }


    @Autowired
    private CKMapper ckMapper;

    //案例三
    @Override
    public ResponseData querySalesAmountToday() {

        List<BrandSaleAmount> res = ckMapper.queryBrandSaleAmount();

        List<String> categories = new ArrayList<>();
        List<Double> brandData = new ArrayList<>();

        //封装series部分
        for (BrandSaleAmount re : res) {
            categories.add(re.getBrand());
            brandData.add(re.getAmount());
        }

        SeriesData<Double> orderSeriesData = new SeriesData<>("手机品牌", brandData);
        ResponseData responseData = new ResponseData("", 0, categories, Arrays.asList(orderSeriesData));

        return responseData;
    }

    //案例四
    @Override
    public ResponseData querySalesAmount() {

        List<BrandSaleAmountCompare> res = ckMapper.queryBrandStats();

        List<String> categories = new ArrayList<>();
        List<Double> todayData = new ArrayList<>();
        List<Double> yesterdayData = new ArrayList<>();

        for (BrandSaleAmountCompare re : res) {
            categories.add(re.getBrand());
            todayData.add(re.getTa());
            yesterdayData.add(re.getYa());
        }

        SeriesData<Double> s1 = new SeriesData<>("昨天", yesterdayData);
        SeriesData<Double> s2 = new SeriesData<>("今天", todayData);

        ResponseData responseData = new ResponseData("", 0, categories, Arrays.asList(s1,s2));

        return responseData;
    }

    @Autowired
    private ESDao esDao;
    //案例五
    @Override
    public JSONObject querySourceData() {

        //封装聚合条件。 按照source来源统计聚合。
        TermsAggregationBuilder agg = AggregationBuilders.terms("sourceCount").field("source").size(10);

        //查询es
        SearchHits<Customer> searchHits = esDao.queryAggFromES(agg, Customer.class);

        //遍历结果
        Aggregations aggregations = searchHits.getAggregations();
        Terms terms = aggregations.get("sourceCount");

        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        //准备封装数据
        JSONObject result = new JSONObject();
        result.put("status",0);
        result.put("msg","");

        ArrayList<JSONObject> data = new ArrayList<>();

        for (Terms.Bucket bucket : buckets) {
            JSONObject jo = new JSONObject();
            jo.put("name",bucket.getKey());
            jo.put("value",bucket.getDocCount());
            data.add(jo);
        }

        result.put("data",data);

        return result;
    }

    //案例六
    @Override
    public JSONObject queryIndustryStats() {

        //封装聚合条件
        TermsAggregationBuilder agg = AggregationBuilders.terms("pc").field("profession").size(10)
                                                                             .subAggregation(
                                                                                 AggregationBuilders.terms("gc").field("sex").size(2)
                                                                             );

        SearchHits<Customer> searchHits = esDao.queryAggFromES(agg, Customer.class);
        Aggregations aggregations = searchHits.getAggregations();

        //封装数据
        List<String> categeries = new ArrayList<>();
        List<Long> maleData = new ArrayList<>();
        List<Long> femaleData = new ArrayList<>();

        //遍历结果
        Terms terms  = aggregations.get("pc");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {

            categeries.add(bucket.getKeyAsString());

            Terms sexTerms =  bucket.getAggregations().get("gc");

            List<? extends Terms.Bucket> sexTermsBuckets = sexTerms.getBuckets();

            //计算男女的比例
            for (Terms.Bucket sexTermsBucket : sexTermsBuckets) {

                if ("男".equals(sexTermsBucket.getKeyAsString())){

                    long percent = sexTermsBucket.getDocCount() * 100 / bucket.getDocCount();
                    maleData.add(percent);
                    femaleData.add(100 - percent);
                }
            }
        }

        //封装最后结果
        SeriesData<Long> s1 = new SeriesData<>("男", maleData);
        SeriesData<Long> s2 = new SeriesData<>("女", femaleData);

        //添加uint字段
        JSONObject j1 = JSON.parseObject(JSON.toJSONString(s1));
        JSONObject j2 = JSON.parseObject(JSON.toJSONString(s2));
        j1.put("unit","%");
        j2.put("unit","%");

        JSONObject result = new JSONObject();
        JSONObject dataJO = new JSONObject();
        result.put("msg","");
        result.put("status",0);
        dataJO.put("categories",categeries);
        dataJO.put("series",Arrays.asList(j1,j2));
        result.put("data",dataJO);

        return result;
    }

    @Autowired
    private HBaseMapper hBaseMapper;
    //案例七
    @Override
    public JSONObject queryProvinceStatToday() {
        List<ProvinceOrderStats> res = hBaseMapper.queryProvinceOrderStats(LocalDate.now().toString());

        JSONObject result = new JSONObject();
        JSONObject dataJO = new JSONObject();
        result.put("msg","");
        result.put("status",0);
        dataJO.put("mapData",res);
        dataJO.put("valueName","下单数");
        dataJO.put("sizeValueName","成交额");

        result.put("data",dataJO);

        return result;
    }

    //案例八
    @Override
    public JSONObject queryProviceCompletion(String city) {

        Double amount = hBaseMapper.queryProvinceAmountToday(LocalDate.now().toString(), city);

        double percent = 0;
        if (amount >= 10000000){
            percent = 100d;
        }else{
             percent = amount * 100 / 10000000d;
        }

        JSONObject result = new JSONObject();
        result.put("msg","");
        result.put("status",0);
        result.put("data",percent);

        return result;
    }

    //提供kafka客户端
    //只要引入了spring-kafka，在容器中会自动提供 kafka客户端对象
    @Autowired
    private KafkaTemplate<String,String> kafkaClient;

    @Value("${di.kakfa.topic}")
    private String topic;

    @Override
    public void saveLogToKafka(String log) {

        kafkaClient.send(topic,log);
    }
}
