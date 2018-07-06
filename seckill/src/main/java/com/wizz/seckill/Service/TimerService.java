package com.wizz.seckill.Service;

import com.wizz.seckill.Mapper.reqInfoMapper;
import com.wizz.seckill.Service.Impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TimerService {

    private static Logger logger = Logger.getLogger(TimerService.class.getName());

    @Autowired
    private reqInfoMapper infomap;

    @Autowired
    private RedisService cacheService;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000)
    public void timerRate() {
        long mysqlSum = infomap.getReqSum() , cacheSum = cacheService.getCnt("curCnt");
        cacheService.setCnt("curCnt", mysqlSum);

    }
}

