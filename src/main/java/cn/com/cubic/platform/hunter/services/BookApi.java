package cn.com.cubic.platform.hunter.services;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import cn.com.cubic.platform.hunter.mongo.models.Book;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;

public interface BookApi {


//    BaseResponse<PageParams<Book>> bookList(PageForm pageForm);


    BaseResponse<Book> bookGet(String id);


}
