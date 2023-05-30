package com.zetyun.tiger.datamock.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "di")
@Data
public class Customer {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String sex;
    @Field(type = FieldType.Keyword)
    private String source;
    @Field(type = FieldType.Keyword)
    private String profession;
}
