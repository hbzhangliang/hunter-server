package cn.com.cubic.platform.hunter.mongo.repo.support;

import cn.com.cubic.platform.hunter.mongo.repo.TmpRepo;
import cn.com.cubic.platform.hunter.mongo.models.Tmp;
import org.springframework.stereotype.Service;


@Service
public class TmpRepoImpl extends BaseMongoDbRepoSupport<Tmp> implements TmpRepo {
}
