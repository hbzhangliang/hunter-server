package cn.com.cubic.platform.a_book.services;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import cn.com.cubic.platform.a_book.mongo.models.Book;
import cn.com.cubic.platform.a_book.mysql.vo.PageForm;
import cn.com.cubic.platform.a_book.mysql.vo.PageParams;

public interface BookApi {


    BaseResponse<PageParams<Book>> bookList(PageForm pageForm);


    BaseResponse<Book> bookGet(String id);


}
