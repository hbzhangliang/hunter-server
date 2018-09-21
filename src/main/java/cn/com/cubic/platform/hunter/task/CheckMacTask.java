package cn.com.cubic.platform.hunter.task;

import cn.com.cubic.platform.hunter.services.UtilTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Liang.Zhang on 2018/9/21.
 **/
@Service
public class CheckMacTask {


    private static final Logger log = LoggerFactory.getLogger(CheckMacTask.class);


    @Autowired
    private UtilTask utilTask;


    /**
     * 验证频率是每天 凌晨1点调用
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void chechMac(){
        log.info("check mac start");
        utilTask.checkMacStatus();
        log.info("check mac end");
    }


}
