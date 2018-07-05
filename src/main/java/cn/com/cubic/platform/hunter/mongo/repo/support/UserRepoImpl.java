package cn.com.cubic.platform.hunter.mongo.repo.support;

import cn.com.cubic.platform.hunter.mongo.models.User;
import cn.com.cubic.platform.hunter.mongo.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 * Created by Liang.Zhang on 2018/7/5.
 **/
@Service
public class UserRepoImpl extends BaseMongoDbRepoSupport<User> implements UserRepo {
}
