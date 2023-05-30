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
public class OrderStats
{
    //分钟级别的时间
    private String time;
    //下单数
    private Integer orderCount;
    //退单数
    private Integer refundCount;
}
