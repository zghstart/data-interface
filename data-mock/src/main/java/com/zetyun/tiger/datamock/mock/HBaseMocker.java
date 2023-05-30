package com.zetyun.tiger.datamock.mock;

import com.apifan.common.random.RandomSource;
import com.apifan.common.random.source.AreaSource;
import com.zetyun.tiger.datamock.mapper.HBaseMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class HBaseMocker implements Runnable {
    @Value("${sleepMs.hbaseMockSleep}")
    private Integer mockSleep;
    @Autowired
    private HBaseMapper mapper;

    @Autowired()
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void exec() {
        taskExecutor.execute(this);
    }

    public HBaseMocker() {
    }

    public void run() {
        String[] brands = {"ZTE", "小米", "VIVO", "OPPO", "华为",
                "IPHONE", "荣耀", "REALME", "IQOO", "ONEPLUS", "MEIZU",
                "努比亚", "ROG", "诺基亚", "天语", "三星"};
        AreaSource areaSource = RandomSource.areaSource();
        this.mapper.createTable();
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        log.info("is running...");

        boolean running = true;
        while(running) {
            this.mapper.insertData(
                    RandomSource.dateTimeSource().randomPastTime(LocalDateTime.now(), 100L).format(df1),
                    brands[RandomUtils.nextInt(0, brands.length)],
                    areaSource.randomProvince(),
                    RandomUtils.nextInt(10, 20),
                    BigDecimal.valueOf(RandomUtils.nextDouble(400000.0, 700000.0)));

            try {
                Thread.sleep((long)this.mockSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
