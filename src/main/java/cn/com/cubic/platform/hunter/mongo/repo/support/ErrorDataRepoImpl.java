package cn.com.cubic.platform.hunter.mongo.repo.support;

import cn.com.cubic.platform.hunter.mongo.repo.ErrorDataRepo;
import cn.com.cubic.platform.hunter.mongo.models.ErrorData;
import org.springframework.stereotype.Service;

@Service
public class ErrorDataRepoImpl extends BaseMongoDbRepoSupport<ErrorData> implements ErrorDataRepo {



}
