package cn.com.cubic.platform.a_book.mongo.repo.support;

import cn.com.cubic.platform.a_book.mongo.repo.ErrorDataRepo;
import cn.com.cubic.platform.a_book.mongo.models.ErrorData;
import org.springframework.stereotype.Service;

@Service
public class ErrorDataRepoImpl extends BaseMongoDbRepoSupport<ErrorData> implements ErrorDataRepo {



}
