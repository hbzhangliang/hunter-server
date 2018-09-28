package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.VProjectDoc;
import cn.com.cubic.platform.hunter.mysql.entity.VProjectDocExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public interface VProjectDocService extends BaseService<VProjectDoc,VProjectDocExample>{

    PageParams<VProjectDoc> list(PageParams<VProjectDoc> pageParams);

    VProjectDoc findById(@NotNull Long id);

}
