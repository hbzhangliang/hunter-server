package cn.com.cubic.platform.hunter.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.TSysConfig;
import cn.com.cubic.platform.hunter.mysql.services.TSysConfigService;
import cn.com.cubic.platform.hunter.mysql.services.impl.TSysConfigServiceImpl;
import cn.com.cubic.platform.hunter.services.UtilTask;
import cn.com.cubic.platform.utils.IpAddressUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Liang.Zhang on 2018/9/21.
 **/
@Service
public class UtilTaskImpl implements UtilTask {

    private final static Logger log = LoggerFactory.getLogger(UtilTaskImpl.class);

    private static final  String macCode="sys.mac";

    private static final String tomcatCode="tomcat.bin";

    @Autowired
    private TSysConfigService tSysConfigService;

    /**
     * 判断当前mac地址，如果变换了，就停止服务
     */
    @Override
    public void checkMacStatus() {
        Boolean flag=true;
        String mac= IpAddressUtils.getLocalMac();
        if(StringUtils.isNotEmpty(mac)){
            TSysConfig config=tSysConfigService.findByCode(macCode);
            //没有查询到数据
            if(config==null){
                TSysConfig bean=new TSysConfig();
                bean.setCode(macCode);
                bean.setValue(mac);
                tSysConfigService.saveOrUpdateNoAccount(bean);
                log.info("插入 sys.mac 数据[{}]",bean);
            }
            else {
                if(!config.getValue().equalsIgnoreCase(mac)){
                    flag=false;
                }
            }
        }
        else {
            flag=false;
        }
        if(flag){
            log.info("sys.mac 验证成功");
        }
        else {
            log.error("sys.mac 验证失败");
            this.stopTomcat();
        }

    }

    @Override
    public void stopTomcat() {
        TSysConfig config=tSysConfigService.findByCode(tomcatCode);
        if(config==null){
            log.error("未配置sysconfig，tomcat.bin");
        }
        this.callShell(config.getValue());
    }


    private Boolean callShell(String shell){
        try{
            if(shell==null||shell.length()==0){
                return true;
            }
            String[] cmd=new String[]{"/bin/sh","-c",shell};

            Process process=Runtime.getRuntime().exec(cmd);

            int exitValue=process.waitFor();
            if(exitValue!=0){
                log.error("命令[{}]执行失败",shell);
            }
            log.info("命令[{}]执行成功",shell);
            return true;
        }
        catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }


}
