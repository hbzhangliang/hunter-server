package cn.com.cubic.platform.hunter.mongo.models;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Book")
public class Book extends BaseMongoDbModel{

    public Book(){}

    public Book(String id,String name,String url){
        this.id=id;
        this.name=name;
        this.url=url;
    }

    public Book(String id,String name,String url,String authorId,String authorName){
        this.id=id;
        this.name=name;
        this.url=url;
        this.authorId=authorId;
        this.authorName=authorName;
    }

    private String authorId;

    private String authorName;

    private String name;

    private String url;

    private String introduction;

    private List<CapterEmt> capterEmtList;


    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<CapterEmt> getCapterEmtList() {
        return capterEmtList;
    }

    public void setCapterEmtList(List<CapterEmt> capterEmtList) {
        this.capterEmtList = capterEmtList;
    }
}
