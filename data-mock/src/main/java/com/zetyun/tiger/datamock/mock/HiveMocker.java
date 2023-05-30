package com.zetyun.tiger.datamock.mock;

import com.apifan.common.random.RandomSource;
import com.apifan.common.random.source.AreaSource;
import com.zetyun.tiger.datamock.bean.HiveData;
import com.zetyun.tiger.datamock.mapper.HiveMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class HiveMocker {
//    @Autowired
    private HiveMapper hiveMapper;
    @Value("${dataCount.hiveDataCount}")
    private Integer hiveDataCount;

    public HiveMocker() {
    }

    public void mock() {
        this.hiveMapper.createTable();
        AreaSource areaSource = RandomSource.areaSource();
        ArrayList<HiveData> datas = new ArrayList<>();

        for(int i = 0; i < this.hiveDataCount; i = i + 1) {
            String[] addrs = areaSource.randomCity(",").split(",");
            datas.add(new HiveData(addrs[0], addrs[1], RandomUtils.nextInt(1, 100)));
        }

        String sql = datas.stream().map(
                (d) -> String.format(" ('%s','%s', %s ) ",
                        d.getProvince(), d.getCity(), d.getPerc())
        ).collect(Collectors.joining(","));
        this.hiveMapper.insertData(LocalDate.now().toString(), sql);
    }
}
