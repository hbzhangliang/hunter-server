package cn.com.cubic.platform.a_book.mongo.models;

import java.io.Serializable;

public class CapterEmt implements Serializable{

    private String name;

    private String url;

    private String content;

    private int sort;


    public CapterEmt(){}

    public CapterEmt(String name,String url){
        this.name=name;
        this.url=url;
    }

    public CapterEmt(String name,String url,String content){
        this.name=name;
        this.url=url;
        this.content=content;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
