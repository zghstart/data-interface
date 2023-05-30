package com.zetyun.tiger.datamock.mock;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@EnableAsync
public class RedisMock implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RedisMock.class);
    @Value("${sleepMs.redisMockSleep}")
    private Integer redisMockSleep;
    @Value("${dest.redisDataKey}")
    private String redisDataKey;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired()
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    public RedisMock() {
    }

    @PostConstruct
    public void exec() {
        taskExecutor.execute(this);
    }

    public void run() {
        log.info("开始生成redis模拟数据");
        String[] brands = {"ZTE", "小米", "VIVO", "OPPO", "华为",
                "IPHONE", "荣耀", "REALME", "IQOO", "ONEPLUS", "MEIZU",
                "努比亚", "ROG", "诺基亚", "天语", "三星"};
        boolean running = true;
        while (running) {
            this.redisTemplate.opsForZSet().incrementScore(
                    this.redisDataKey,
                    brands[RandomUtils.nextInt(0, brands.length)],
                    1.0);

            try {
//                log.info("redisTemplate.getKeySerializer() : {}",redisTemplate.getKeySerializer());
//                log.info("redisTemplate.getValueSerializer() : {}",redisTemplate.getValueSerializer());
//                log.info("redisTemplate.getHashKeySerializer() : {}",redisTemplate.getHashKeySerializer());
//                log.info("redisTemplate.getHashValueSerializer() : {}",redisTemplate.getHashValueSerializer());

                Thread.sleep((long) this.redisMockSleep);
//                log.info("{},redis模拟数据任务正在运行......", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

    public void print() {
        System.out.println(this.redisDataKey);
        System.out.println(this.redisTemplate);
        System.out.println(this.redisMockSleep);
    }
}
