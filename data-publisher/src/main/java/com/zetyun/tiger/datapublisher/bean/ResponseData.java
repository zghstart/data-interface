package com.zetyun.tiger.datapublisher.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Smexy on 2023
 */
@Data
@NoArgsConstructor
public class ResponseData
{
    private String msg;
    private Integer status;
    private JSONObject data;

    public ResponseData(String msg, Integer status, List<String> categories,List<SeriesData> series){

        this.msg = msg;
        this.status = status;

        JSONObject dataJO = new JSONObject();
        dataJO.put("series",series);
        dataJO.put("categories",categories);

        this.data = dataJO;

    }
}
