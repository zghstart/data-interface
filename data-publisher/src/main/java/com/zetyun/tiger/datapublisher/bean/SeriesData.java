package com.zetyun.tiger.datapublisher.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Smexy on 2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesData<T>
{
    private String name;
    private List<T> data;
}
