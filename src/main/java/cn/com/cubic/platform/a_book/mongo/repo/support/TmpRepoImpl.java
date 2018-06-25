package cn.com.cubic.platform.a_book.mongo.repo.support;

import cn.com.cubic.platform.a_book.mongo.repo.TmpRepo;
import cn.com.cubic.platform.a_book.mongo.models.Tmp;
import org.springframework.stereotype.Service;


@Service
public class TmpRepoImpl extends BaseMongoDbRepoSupport<Tmp> implements TmpRepo {
}
