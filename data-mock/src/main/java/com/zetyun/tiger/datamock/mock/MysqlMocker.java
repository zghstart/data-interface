package com.zetyun.tiger.datamock.mock;

import com.zetyun.tiger.datamock.mapper.MysqlMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MysqlMocker implements Runnable {
    @Autowired
    private MysqlMapper mapper;
    @Value("${sleepMs.mysqlMockSleep}")
    private Integer mockSleep;


    @Autowired()
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void exec() {
        taskExecutor.execute(this);
    }

    public MysqlMocker() {
    }

    public void run() {
        this.mapper.createTable();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        boolean running = true;
        while (running) {
            this.mapper.insertData(
                    LocalDateTime.now().format(dateTimeFormatter),
                    RandomUtils.nextInt(1, 8),
                    RandomUtils.nextInt(1, 4));

            try {
                Thread.sleep((long) this.mockSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
