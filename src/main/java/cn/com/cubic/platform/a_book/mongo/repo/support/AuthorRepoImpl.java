package cn.com.cubic.platform.a_book.mongo.repo.support;

import cn.com.cubic.platform.a_book.mongo.repo.AuthorRepo;
import cn.com.cubic.platform.a_book.mongo.models.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorRepoImpl extends BaseMongoDbRepoSupport<Author> implements AuthorRepo {
}
