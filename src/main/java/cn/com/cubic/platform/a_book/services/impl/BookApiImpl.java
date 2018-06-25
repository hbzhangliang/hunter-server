package cn.com.cubic.platform.a_book.services.impl;

import cn.com.cubic.platform.a_book.mongo.repo.BookRepo;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import cn.com.cubic.platform.a_book.mongo.models.Book;
import cn.com.cubic.platform.a_book.mysql.vo.PageForm;
import cn.com.cubic.platform.a_book.mysql.vo.PageParams;
import cn.com.cubic.platform.a_book.services.BookApi;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookApiImpl implements BookApi {


    @Override
    public BaseResponse<PageParams<Book>> bookList(PageForm pageForm) {
        BaseResponse<PageParams<Book>> result = null;
        try {
            int startPoint=(pageForm.getPage()-1)*pageForm.getPageSize();
            int length=pageForm.getPageSize();
            String bookName=pageForm.getParams().get("name")==null?null:pageForm.getParams().get("name").toString();
            Criteria criteria=new Criteria();
            if(StringUtils.isNotEmpty(bookName)){
//                criteria=Criteria.where("name").regex(".*?\\" +bookName+ ".*").orOperator(Criteria.where("authorName").regex(".*?\\" +bookName+ ".*"));
                criteria.orOperator(Criteria.where("name").regex(".*?\\" +bookName+ ".*"),Criteria.where("authorName").regex(".*?\\" +bookName+ ".*"));
            }

            DBObject dbObject = new BasicDBObject();
            BasicDBObject fieldsObject=new BasicDBObject();
            fieldsObject.put("_id", true);
            fieldsObject.put("name", true);
            fieldsObject.put("authorId", true);
            fieldsObject.put("authorName", true);
            Query query = new BasicQuery(dbObject,fieldsObject).addCriteria(criteria).with(new Sort(Sort.Direction.DESC,"id")).skip(startPoint).limit(length);

//            Query query=new Query(criteria).with(new Sort(Sort.Direction.DESC,"id")).skip(startPoint).limit(length);

            List<Book> books=bookRepo.list(query);
            int count=(int)bookRepo.count(new Query(criteria));
            PageParams<Book> tmp=new PageParams<>();
            tmp.setData(books);
            tmp.setPage(pageForm.getPage());
            tmp.setPageSize(pageForm.getPageSize());
            tmp.setTatalRows(count);
            tmp.setTotalPage(count/length+1);
            result=new BaseResponse<PageParams<Book>>("查询数据成功",tmp);
        }
        catch (Exception e){
            e.printStackTrace();
            result=new BaseResponse<>(e.getMessage(),null);
        }
        return result;
    }


    @Override
    public BaseResponse<Book> bookGet(String id) {
        return new BaseResponse<>("查询数据成功",bookRepo.find(id));
    }

    @Autowired
    private BookRepo bookRepo;
}
