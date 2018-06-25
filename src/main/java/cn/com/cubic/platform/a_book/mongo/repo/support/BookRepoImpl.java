package cn.com.cubic.platform.a_book.mongo.repo.support;

import cn.com.cubic.platform.a_book.mongo.repo.BookRepo;
import cn.com.cubic.platform.a_book.mongo.models.Book;
import org.springframework.stereotype.Service;

@Service
public class BookRepoImpl extends BaseMongoDbRepoSupport<Book> implements BookRepo {
}
