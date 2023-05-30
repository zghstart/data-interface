package com.zetyun.tiger.datamock.mock;

import com.apifan.common.random.RandomSource;
import com.apifan.common.random.source.AreaSource;
import com.apifan.common.random.source.EducationSource;
import com.apifan.common.random.source.OtherSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.zetyun.tiger.datamock.bean.LogData;
import com.zetyun.tiger.datamock.util.HttpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class LogMocker implements Runnable {
    @Value("${sleepMs.logSleep}")
    private Integer logSleep;
    @Value("${dest.logUrl}")
    private String logUrl;
    @Value("${dest.param}")
    private String paramName;

    @Autowired()
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void exec() {
        taskExecutor.execute(this);
    }


    public LogMocker() {
    }

    public void run() {
        Gson gson = new Gson();
        HttpUtil httpUtil = new HttpUtil(this.logUrl);
        AreaSource areaSource = RandomSource.areaSource();
        EducationSource educationSource = RandomSource.educationSource();
        OtherSource otherSource = RandomSource.otherSource();
        PersonInfoSource personInfoSource = RandomSource.personInfoSource();

        boolean running = true;
        while (running) {
            LogData logData = new LogData(
                    areaSource.randomProvince(),
                    personInfoSource.randomChineseName(),
                    educationSource.randomCollege(),
                    educationSource.randomDegree(),
                    otherSource.randomPlateNumber(
                            RandomUtils.nextBoolean()),
                    personInfoSource.randomChineseNickName(8),
                    personInfoSource.randomQQAccount(),
                    System.currentTimeMillis());
            try {
                String data = gson.toJson(logData);
                httpUtil.get(data, this.paramName);

//                log.info("send to kafka {}",data);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep((long) this.logSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
