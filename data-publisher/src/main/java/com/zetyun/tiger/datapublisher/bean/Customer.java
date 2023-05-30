package com.zetyun.tiger.datapublisher.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Smexy on 2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//将Bean和指定的Index(es中的表)进行映射
@Document(indexName = "di")
public class Customer
{
    @Id
    //当前字段是主键
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String sex;
    @Field(type = FieldType.Keyword)
    private String profession;
    @Field(type = FieldType.Keyword)
    private String source;
}
