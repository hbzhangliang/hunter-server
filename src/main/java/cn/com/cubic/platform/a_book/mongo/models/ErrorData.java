package cn.com.cubic.platform.a_book.mongo.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ErrorData")
public class ErrorData extends BaseMongoDbModel{

    public ErrorData(String id,String url){
        this.bookId=id;
        this.bookUrl=url;
    }

    private String bookId;
    private String bookUrl;


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }
}
