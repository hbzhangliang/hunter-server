package cn.com.cubic.platform.hunter.mongo.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/7/5.
 **/
@Document(collection = "User")
public class User extends BaseMongoDbModel{

    private String name;

    private String gengder;

    private String psd;

    private Object ext;

    private List<group> groupList=new ArrayList<>(5);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGengder() {
        return gengder;
    }

    public void setGengder(String gengder) {
        this.gengder = gengder;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public List<group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<group> groupList) {
        this.groupList = groupList;
    }


    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }
}
