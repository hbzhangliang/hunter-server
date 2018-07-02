package cn.com.cubic.platform.hunter.mongo.repo.support;

import cn.com.cubic.platform.hunter.mongo.repo.AuthorRepo;
import cn.com.cubic.platform.hunter.mongo.models.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorRepoImpl extends BaseMongoDbRepoSupport<Author> implements AuthorRepo {
}
