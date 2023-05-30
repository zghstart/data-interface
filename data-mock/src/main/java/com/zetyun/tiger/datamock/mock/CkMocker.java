package com.zetyun.tiger.datamock.mock;

import com.apifan.common.random.RandomSource;
import com.zetyun.tiger.datamock.mapper.CKMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CkMocker implements Runnable {
    @Value("${sleepMs.ckMockSleep}")
    private Integer mockSleep;
    @Autowired
    private CKMapper mapper;


    @Autowired()
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void exec() {
        taskExecutor.execute(this);
    }

    public CkMocker() {
    }

    public void run() {
        String[] brands = {"ZTE", "小米", "VIVO", "OPPO", "华为",
                "IPHONE", "荣耀", "REALME", "IQOO", "ONEPLUS", "MEIZU",
                "努比亚", "ROG", "诺基亚", "天语", "三星"};
        double[] amount = {2999.0, 3999.0, 4399.0, 5999.0, 4456, 5678, 10100, 10086};
        this.mapper.createTable();
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = null;
        boolean running = true;

        while (running) {
            int flag = RandomUtils.nextInt(0, 3);
            switch (flag) {
                case 0:
                    // 七天之内的随机时间
                    dateTime = RandomSource.dateTimeSource().randomPastTime(7);
                    break;
                case 1:
                    // 36-24 小时之间
                    dateTime = RandomSource.dateTimeSource().randomPastTime(LocalDateTime.now().minusDays(1L), 7200L);
                    break;
                case 2:
                    // 100秒之内的时间
                    dateTime = RandomSource.dateTimeSource().randomPastTime(LocalDateTime.now(), 100L);
                    break;
                default:
                    throw new IllegalArgumentException("It will never happen");
            }

            this.mapper.insertData(
                    dateTime.format(df1),
                    brands[RandomUtils.nextInt(0, brands.length)],
                    BigDecimal.valueOf(amount[RandomUtils.nextInt(0, amount.length)]),
                    dateTime.format(df2));

            try {
                Thread.sleep((long) this.mockSleep);
            } catch (InterruptedException var8) {
                var8.printStackTrace();
                running = false;
            }
        }
    }


}
