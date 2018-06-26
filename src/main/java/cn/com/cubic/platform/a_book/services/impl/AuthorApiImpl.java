package cn.com.cubic.platform.a_book.services.impl;

import cn.com.cubic.platform.a_book.mongo.models.*;
import cn.com.cubic.platform.a_book.mongo.repo.AuthorRepo;
import cn.com.cubic.platform.a_book.mongo.repo.ErrorDataRepo;
import cn.com.cubic.platform.utils.*;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import cn.com.cubic.platform.a_book.mongo.repo.BookRepo;
import cn.com.cubic.platform.a_book.mysql.vo.PageForm;
import cn.com.cubic.platform.a_book.mysql.vo.PageParams;
import cn.com.cubic.platform.a_book.services.AuthorApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Service
public class AuthorApiImpl implements AuthorApi {

    private static final Logger log = LoggerFactory.getLogger(AuthorApiImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @Override
    public void generateAuthor() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                    Future<Set<Author>> list = taskExecutor.submit(new Callable<Set<Author>>() {
                        @Override
                        public Set<Author> call() throws Exception {
                            Set<Author> result = new HashSet<>(100);
                            for(int i=1;i<10;i++) {
                                String strUrl = String.format("http://www.kanunu8.com/author%s.html", i);
                                log.info("url is[{}]", strUrl);
                                String content = JSONHelper.loadJson(strUrl);
                                List<Map<String, String>> list = i!=3? HtmlUtilsZj.getAuthList(content): HtmlUtilsZj.getAuthListADD(content);
                                log.info("when i is [{}]result[{}]",i,JSON.toJSONString(list));
                                for (Map<String, String> item : list) {
                                    String name = item.get("name");
                                    String url = item.get("url");
                                    Author author = new Author(name, url);
                                    result.add(author);
                                }
                            }

                            //作家列表里面的添加
                            for(int i=1;i<12;i++) {
                                String strUrl = String.format("http://www.kanunu8.com/files/writer/18-%s.html", i);
                                String content = JSONHelper.loadJson(strUrl);
                                content = HtmlAuthor.delBlank(content);
                                content=HtmlAuthor.getTableContent(content);
                                List<String> tbList = HtmlAuthor.tagInfo(content);
                                List<Map<String, String>> mapList = HtmlAuthor.tagADetail(tbList);
                                for(Map<String,String> item:mapList){
                                    String name = item.get("name");
                                    String url = item.get("url");
                                    Author author = new Author(name, url);
                                    result.add(author);
                                }

                            }


                            return result;
                        }
                    });


                    try {
                        Boolean flag = true;
                        while (flag) {
                            //完成且没有取消
                            if (list.isDone() && !list.isCancelled()) {
                                flag = false;
                                Set<Author> result = list.get();
                                //清除重复数据
                                Set<Author> AuthorList=new HashSet<>(1000);
                                for(Author item:result){
                                    Boolean tmpFlag=false;
                                    if(AuthorList!=null){
                                        for(Author pp:AuthorList){
                                            if(item.getName().equals(pp.getName())){
                                                tmpFlag=true;
                                                break;
                                            }
                                        }
                                    }
                                    if(!tmpFlag){ //不存在才加入
                                        AuthorList.add(item);
                                    }
                                }
                                authorRepo.save(AuthorList);
                            }
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }


    /**
     * 总计962 条
     * 三类
     * /files/    953条
     * /author/    4条
     * /zj/    4条
     * /hmtl/   唐家三少    1条
     */
    @Override
    public void filterAuthor() {
        List<Author> list=authorRepo.list();
        List<Author> filesList=new ArrayList<>(1000);
        List<Author>  authorList=new ArrayList<>(10);
        List<Author> zjList=new ArrayList<>(10);
        List<Author> otherList=new ArrayList<>(10);
        for(Author item:list){
            String url=item.getUrl().toLowerCase();
            if(url.indexOf("files")>-1){
                filesList.add(item);
            }
            else if(url.indexOf("author")>-1){
                authorList.add(item);
            }
            else if(url.indexOf("zj")>-1){
                zjList.add(item);
            }else {
                otherList.add(item);
            }
        }

        log.info("contains auth [{}]",JSON.toJSONString(authorList));

        log.info("contains zj [{}]",JSON.toJSONString(zjList));

        log.info("contains others  [{}]",JSON.toJSONString(otherList));
    }


    @Override
    public void generateBooks() {
        try {
            List<Author> authorList = authorRepo.list();
            //分为三类
            List<Book> bookList = new ArrayList<>(1000);
            for (Author item : authorList) {
                String url = item.getUrl();
                if (url.indexOf("kanunu") == -1) {
                    url = UtilHelper.contactHostUrl(this.host, url);
                }
                List<Map<String, String>> tmp = HtmlUtils.getBookNameAndUrl(url);
                if(null==tmp){
                    continue;
                }
                log.info("generateBooks url [{}],books[{}]", url, JSONObject.toJSONString(tmp));



                List<BookEmt> emtList = new ArrayList<>(10);

                if (tmp != null && tmp.size() > 0) {
                    for (Map<String, String> tp : tmp) {
                        String strOjectId = ObjectId.get().toString();
                        String strName = tp.get("name");
                        String strUrl = tp.get("url");

                        Book book = new Book(strOjectId, strName, strUrl, item.getId(), item.getName());
                        bookList.add(book);


                        BookEmt bookEmt = new BookEmt(strOjectId, strName, strUrl);
                        emtList.add(bookEmt);
                    }
                }
                item.setBookList(emtList);
            }
            authorRepo.saveBatch(authorList);
            bookRepo.saveBatch(bookList);
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("generateBooks run error [{}]",e.getMessage());
        }
    }


    @Override
    public void generateCapter() {
        int count=bookRepo.list().size();
        int length=50;
        int size=count%length==0?count/length:count/length+1;
        int startPoint=0;
        for(int i=1280/50;i<size;i++) {
            startPoint=i*length;
            Query query = new Query().with(new Sort(Sort.Direction.ASC, "id")).skip(startPoint).limit(length);
            List<Book> books = bookRepo.list(query);
            int bookIndex=0;
            for (Book item : books) {
                String url = item.getUrl();
                if (url.indexOf("www") == -1) {
                    url = UtilHelper.contactHostUrl(host, url);
                }
                bookIndex++;
                log.info("book url is [{}],第[{}]本书，进行了 [{}] ", url,bookIndex+startPoint,1.0*(bookIndex+startPoint)/count);
                List<Map<String, String>> tmp = HtmlUtils.getCapterNameAndUrl(url);

                List<CapterEmt> capterEmtList = new ArrayList<>(100);

                if (tmp != null && tmp.size() > 0) {
                    for (Map<String, String> pt : tmp) {
                        String tmpUrl = "";
                        if (url.endsWith("html")) {
                            url = url.substring(0, url.lastIndexOf("/") + 1);
                            tmpUrl = UtilHelper.contactHostUrl(url, pt.get("url"));
                        } else {
                            tmpUrl = UtilHelper.contactHostUrl(url, pt.get("url"));
                        }
//                        log.info("capter url is [{}]", tmpUrl);
                        CapterEmt emt = new CapterEmt(pt.get("name"), pt.get("url"), HtmlUtils.getCapterContent(tmpUrl));
//                        log.info("emt is [{}]",JSONObject.toJSONString(emt));
                        capterEmtList.add(emt);
                    }

                }
                item.setCapterEmtList(capterEmtList);
            }
            bookRepo.saveBatch(books);
        }
    }


    @Override
    public void generateCapterSync() {

        this.threadIndex=0;

        //er个线程跑
        int threadCount=3;
        for(int i=0;i<threadCount;i++){
            taskExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    int startPoint=500*getThreadIndex();
                    addTreadIndex();
                    int length=500;
                    log.info("startpoint is [{}]",startPoint);
                    Query query = new Query(Criteria.where("capterEmtList").exists(false)).with(new Sort(Sort.Direction.ASC, "id")).skip(startPoint).limit(length);
                    List<Book> books = bookRepo.list(query);

                    List<ErrorData> errorDataList=new ArrayList<>(100);
                    for (Book item : books) {
                        String url = item.getUrl();
                        try {
                            if (url.indexOf("www") == -1) {
                                url = UtilHelper.contactHostUrl(host, url);
                            }
                            log.info("book url is [{}]", url);
                            List<Map<String, String>> tmp = HtmlUtils.getCapterNameAndUrl(url);

                            List<CapterEmt> capterEmtList = new ArrayList<>(100);

                            if (tmp != null && tmp.size() > 0) {
                                for (Map<String, String> pt : tmp) {
                                    String t = pt.get("content");
                                    if (StringUtils.isNotEmpty(t)) {
                                        CapterEmt emt = new CapterEmt(pt.get("name"), pt.get("url"), t);
                                        log.info("emt is [{}]", emt);
                                        capterEmtList.add(emt);
                                    } else {
                                        String tmpUrl = "";
                                        if (url.endsWith("html")) {




                                            url = url.substring(0, url.lastIndexOf("/") + 1);
                                            tmpUrl = UtilHelper.contactHostUrl(url, pt.get("url"));
                                        } else {
                                            tmpUrl = UtilHelper.contactHostUrl(url, pt.get("url"));
                                        }

                                        CapterEmt emt = new CapterEmt(pt.get("name"), pt.get("url"), HtmlUtils.getCapterContent(tmpUrl));
                                        log.info("emt is [{}]", emt);
                                        capterEmtList.add(emt);
                                    }
                                }
                            }
                            item.setCapterEmtList(capterEmtList);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            errorDataList.add(new ErrorData(item.getId(),item.getUrl()));
                        }

                    }
                    errorDataRepo.saveBatch(errorDataList);
                    bookRepo.saveBatch(books);

                }
            });
        }


    }


    private String contactCapterUrl(String bookUrl,String capUrl){
        int startPoint=capUrl.indexOf("/"),endPoint=capUrl.indexOf("/",startPoint);
        //不存在
        if(startPoint==-1||endPoint==-1){
            bookUrl = bookUrl.substring(0, bookUrl.lastIndexOf("/") + 1);
            return UtilHelper.contactHostUrl(bookUrl, capUrl);
        }


        if(startPoint==0){
            bookUrl = bookUrl.substring(0, bookUrl.lastIndexOf(capUrl.substring(startPoint,endPoint)) + 1);
            return UtilHelper.contactHostUrl(bookUrl, capUrl);
        }
        else {

            return null;
        }


    }


    /**
     *
     * @param pageForm
     * @return
     */
    @Override
    public BaseResponse<PageParams<Author>> authorList(PageForm pageForm) {
        BaseResponse<PageParams<Author>> result = null;
        try {
            int startPoint=(pageForm.getPageNum()-1)*pageForm.getNumPerPage();
            int length=pageForm.getNumPerPage();
            String authorName=pageForm.getParams().get("name")==null?null:pageForm.getParams().get("name").toString();
            Criteria criteria=new Criteria();
            if(StringUtils.isNotEmpty(authorName)){
                criteria=Criteria.where("name").regex(".*?\\" +authorName+ ".*");
            }

            DBObject dbObject = new BasicDBObject();
            BasicDBObject fieldsObject=new BasicDBObject();
            fieldsObject.put("_id", true);
            fieldsObject.put("name", true);
            Query query = new BasicQuery(dbObject,fieldsObject).addCriteria(criteria).with(new Sort(Sort.Direction.DESC,"id")).skip(startPoint).limit(length);


            List<Author> authors=authorRepo.list(query);
            int count=(int)authorRepo.count(new Query(criteria));
            PageParams<Author> tmp=new PageParams<>();
            tmp.setData(authors);
            tmp.setCurrentPage(pageForm.getPageNum());
//            tmp.setNumPerPage(pageForm.getNumPerPage());
//            tmp.setTotalCount(count);
            tmp.setTotalPage(count/length+1);
            result=new BaseResponse<PageParams<Author>>("查询数据成功",tmp);
        }
        catch (Exception e){
            e.printStackTrace();
            result=new BaseResponse<>(e.getMessage(),null);
        }
        return result;
    }


    @Override
    public BaseResponse<Author> authorGet(String id) {
//        authorRepo.find(id);
        return  new BaseResponse<>("查询成功",authorRepo.find(id));
    }

    private synchronized void  addTreadIndex(){
        this.threadIndex++;
    }

    private synchronized int getThreadIndex(){
        return this.threadIndex;
    }

    private int threadIndex=0;

    private String host="http://www.kanunu8.com";


    @Autowired
    private ErrorDataRepo errorDataRepo;


    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

}
