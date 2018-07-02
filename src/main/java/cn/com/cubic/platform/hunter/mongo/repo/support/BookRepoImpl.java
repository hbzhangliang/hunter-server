package cn.com.cubic.platform.hunter.mongo.repo.support;

import cn.com.cubic.platform.hunter.mongo.repo.BookRepo;
import cn.com.cubic.platform.hunter.mongo.models.Book;
import org.springframework.stereotype.Service;

@Service
public class BookRepoImpl extends BaseMongoDbRepoSupport<Book> implements BookRepo {
}
