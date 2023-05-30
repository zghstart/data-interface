package com.zetyun.tiger.datapublisher.dao.impl;

import com.zetyun.tiger.datapublisher.dao.ESDao;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

/**
 * Created by Smexy on 2023
 *
 *  @Repository 和 @Mapper的作用是一样的。
 *      @Repository 适用于 所有的 Dao
 *      @Mapper 局限于 JDBC中使用Mybatis的Dao
 */
@Repository
public class ESDaoImpl implements ESDao
{

    /*
        准备客户端连接ES
        如果已经引入了 spring-data-elasticsearch，在容器中自动提供一个操作ES的客户端对象。
     */
    @Autowired
    private ElasticsearchRestTemplate esClient;

    @Override
    public <T> SearchHits<T> queryAggFromES(AbstractAggregationBuilder agg, Class<T> t) {

        //根据聚合条件agg构造查询对象
        NativeSearchQuery query = new NativeSearchQueryBuilder().addAggregation(agg).build();

        return esClient.search(query, t);
    }
}
