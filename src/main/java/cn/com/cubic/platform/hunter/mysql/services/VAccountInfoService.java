package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TSysAccountExample;
import cn.com.cubic.platform.hunter.mysql.entity.TSysTeam;
import cn.com.cubic.platform.hunter.mysql.entity.VAccountInfo;
import cn.com.cubic.platform.hunter.mysql.entity.VAccountInfoExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/29.
 **/

public interface VAccountInfoService extends BaseService<VAccountInfo,VAccountInfoExample>{

    PageParams<VAccountInfo> list(PageParams<VAccountInfo> pageParams);

    List<VAccountInfo> listAll();

    VAccountInfo findById(@NotNull Long id);
}
