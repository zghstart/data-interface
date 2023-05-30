package com.zetyun.tiger.datamock.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProvinceStat {
    private String province;
    private BigDecimal saleAmount;
    private Integer totalOrderCount;

}
