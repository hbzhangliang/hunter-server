package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TLogLogin extends BaseEntity{
    private Long accountId;

    private String loginAccount;

    private String hostName;

    private String ipAddress;

    private String macAddress;

    private String city;

    private String sysInfo;

    private String browser;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSysInfo() {
        return sysInfo;
    }

    public void setSysInfo(String sysInfo) {
        this.sysInfo = sysInfo;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

}