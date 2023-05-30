package com.zetyun.tiger.datapublisher.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Smexy on 2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceOrderStats
{
    private String name;
    private Integer value;
    private Double sizeValue;
    private String url = "sugar.baidu.com";
}
