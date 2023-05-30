package com.zetyun.tiger.datamock.mapper;

import com.zetyun.tiger.datamock.bean.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESMapper extends ElasticsearchRepository<Customer, Integer> {
}