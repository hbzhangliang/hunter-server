package cn.com.cubic.platform.hunter.mongo.models;

import java.io.Serializable;

/**
 * Created by Liang.Zhang on 2018/7/26.
 **/

public class group implements Serializable {
    private String id;
    private String  name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
