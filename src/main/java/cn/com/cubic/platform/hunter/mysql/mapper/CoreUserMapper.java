package cn.com.cubic.platform.hunter.mysql.mapper;

import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.entity.CoreUserExample;

import java.util.List;

public interface CoreUserMapper extends BaseMapper<CoreUser,CoreUserExample>{

    CoreUser selectById(Long id);


    List<CoreUser> storeProc();

}