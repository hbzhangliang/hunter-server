package cn.com.cubic.platform.utils.sso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Repository
public class Config {

    @Value("${sso.loginurl}")
    private String loginUrl;

    @Value("${sso.redirecturl}")
    private String redirectUrl;

    @Value("${sso.excludePath}")
    private String  excludePath;


    @Bean
    public SSOConfig ssoConfig(){
        SSOConfig ssoConfig=new SSOConfig();
        ssoConfig.setLoginUrl(loginUrl);
        ssoConfig.setExcludePaths(excludePath);
        ssoConfig.setRedirectUrl(redirectUrl);
        return ssoConfig;
    }
}
