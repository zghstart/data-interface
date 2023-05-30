package com.zetyun.tiger.datamock.mock;

import com.apifan.common.random.RandomSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.zetyun.tiger.datamock.bean.Customer;
import com.zetyun.tiger.datamock.mapper.ESMapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ESMocker implements Runnable {
    @Autowired
    private ESMapper esMapper;
    @Value("${sleepMs.esMockSleep}")
    private Integer mockSleep;

    @Autowired()
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void exec() {
        taskExecutor.execute(this);
    }


    public ESMocker() {
    }

    public void run() {
        Customer customer = new Customer();
        PersonInfoSource personInfoSource = RandomSource.personInfoSource();
        int id = 0;
        boolean running = true;
        String[] source = {"搜索引擎", "直接访问", "邮件营销", "联盟广告", "视频广告","抖音直播"};
        String[] profession = {"电子网络", "销售", "教育", "保险", "银行", "建筑", "传媒", "程序员", "潍坊银行内鬼"};

        while (running) {
            int sexRand = RandomUtils.nextInt(0, 2);
            customer.setId(id++);
            if (sexRand == 0) {
                customer.setSex("男");
                customer.setName(personInfoSource.randomMaleChineseName());
            } else {
                customer.setSex("女");
                customer.setName(personInfoSource.randomFemaleChineseName());
            }

            customer.setSource(source[RandomUtils.nextInt(0, source.length)]);
            customer.setProfession(profession[RandomUtils.nextInt(0, profession.length)]);
            this.esMapper.save(customer);

            try {
                Thread.sleep((long) this.mockSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
