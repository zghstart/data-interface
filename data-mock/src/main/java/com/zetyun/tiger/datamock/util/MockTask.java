package com.zetyun.tiger.datamock.util;

import com.zetyun.tiger.datamock.mock.CkMocker;
import com.zetyun.tiger.datamock.mock.ESMocker;
import com.zetyun.tiger.datamock.mock.HBaseMocker;
import com.zetyun.tiger.datamock.mock.LogMocker;
import com.zetyun.tiger.datamock.mock.MysqlMocker;
import com.zetyun.tiger.datamock.mock.RedisMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

//@Component
public class MockTask {
    @Value("${dataCount.mockCount}")
    private Integer mock_count;
    @Autowired
    @Qualifier("taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private RedisMock redisMock;
    @Autowired
    private MysqlMocker mysqlMocker;
    @Autowired
    private CkMocker ckMocker;
    @Autowired
    private HBaseMocker hBaseMocker;
    @Autowired
    private ESMocker esMocker;
    @Autowired
    private LogMocker logMocker;

    public MockTask() {
    }

    @Async
    public void mainTask() {
        for (int i = 0; i < this.mock_count; ++i) {
            this.taskExecutor.execute(this.redisMock);
            this.taskExecutor.execute(this.ckMocker);
            this.taskExecutor.execute(this.hBaseMocker);
            this.taskExecutor.execute(this.esMocker);
            this.taskExecutor.execute(this.mysqlMocker);
            this.taskExecutor.execute(this.logMocker);
        }

        while (true) {
            try {
                Thread.sleep(1000L);
                if (this.taskExecutor.getActiveCount() == 0) {
                    this.taskExecutor.destroy();
                    return;
                }
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }
    }
}
