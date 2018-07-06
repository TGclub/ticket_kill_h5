package com.wizz.seckill.Service;

import com.wizz.seckill.Mapper.actAttrMapper;
import com.wizz.seckill.Model.actAtr;
import com.wizz.seckill.Service.Impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class initTicketsService implements ApplicationRunner {


    private static Logger logger = Logger.getLogger(initTicketsService.class.getName());

    @Autowired
    actAttrMapper actamap;

    @Autowired
    private RedisService cacheService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Integer id = actamap.recent();
        if(id == null){
            return ;
        }
        logger.log(Level.INFO,"id : "+ id);
        actAtr aa = actamap.getActAttrById(id);
        logger.log(Level.INFO,"tickets: " + aa.getTickets());
        cacheService.set("ticketsSum","" + aa.getTickets());
        cacheService.set("begtime", aa.getSeckilltime());

        //cacheService.set("begtime",);
    }
}
