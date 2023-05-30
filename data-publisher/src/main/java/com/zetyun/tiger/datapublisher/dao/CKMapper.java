package com.zetyun.tiger.datapublisher.dao;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.zetyun.tiger.datapublisher.bean.BrandSaleAmount;
import com.zetyun.tiger.datapublisher.bean.BrandSaleAmountCompare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Smexy on 2023
 */
@Mapper
@DS("ck")
public interface CKMapper
{
    @Select("select" +
        "  brand," +
        "  sum(saleAmount) amount " +
        " from di" +
        " where toDate(dt) = today()" +
        " group by brand")
    List<BrandSaleAmount> queryBrandSaleAmount();
    
    @Select("select" +
        "       ifNull(t1.brand,t2.brand) brand," +
        "       ifNull(ta,0.0) ta," +
        "       ifNull(ya,0.0) ya" +
        " from" +
        " (select brand,sum(saleAmount) ta from di where toDate(dt) = today() group by brand) t1" +
        " full join" +
        " (select brand,sum(saleAmount) ya from di where toDate(dt) = yesterday() group by brand) t2" +
        " on t1.brand = t2.brand")
    List<BrandSaleAmountCompare> queryBrandStats();
}
