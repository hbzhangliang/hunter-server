package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.VShareProject;
import cn.com.cubic.platform.hunter.mysql.entity.VShareProjectExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public interface VShareProjectService extends BaseService<VShareProject,VShareProjectExample>{

    PageParams<VShareProject> list(PageParams<VShareProject> pageParams);

    VShareProject findById(@NotNull Long id);

}
