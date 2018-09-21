package cn.com.cubic.platform.hunter.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Liang.Zhang on 2018/9/21.
 **/
@Service
public class CheckMacTask {


    private static final Logger log = LoggerFactory.getLogger(CheckMacTask.class);



    @Scheduled(cron = "*/2 * * * * ?")
    public void doTest(){
        log.info("task do ");
    }


}
