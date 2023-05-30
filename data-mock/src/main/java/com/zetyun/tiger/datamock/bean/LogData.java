package com.zetyun.tiger.datamock.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogData {
    private String province;
    private String user;
    private String college;
    private String degree;
    private String carNum;
    private String nickName;
    private String qq;
    private Long ts;

}
