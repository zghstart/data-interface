package com.zetyun.tiger.datapublisher.dao;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;

/**
 * Created by Smexy on 2023
 *
 *  ES是Nosql数据库，无法使用 Mybatis
 */
public interface ESDao
{
   <T> SearchHits<T> queryAggFromES(AbstractAggregationBuilder agg,Class<T> t);
}
