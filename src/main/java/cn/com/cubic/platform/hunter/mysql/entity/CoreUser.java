package cn.com.cubic.platform.hunter.mysql.entity;


public class CoreUser  extends BaseEntity{

    private String code;

    private String name;

    private String psd;

    private String type;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}