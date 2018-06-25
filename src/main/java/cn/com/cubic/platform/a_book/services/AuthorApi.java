package cn.com.cubic.platform.a_book.services;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import cn.com.cubic.platform.a_book.mongo.models.Author;
import cn.com.cubic.platform.a_book.mysql.vo.PageForm;
import cn.com.cubic.platform.a_book.mysql.vo.PageParams;

public interface AuthorApi {


    void generateAuthor();


    void filterAuthor();


    void generateBooks();



    void generateCapter();


    //异步调用
    void generateCapterSync();




    BaseResponse<PageParams<Author>> authorList(PageForm pageForm);


    BaseResponse<Author> authorGet(String id);

}
