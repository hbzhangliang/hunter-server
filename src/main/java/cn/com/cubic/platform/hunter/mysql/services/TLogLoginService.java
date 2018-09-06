package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TLogLogin;
import cn.com.cubic.platform.hunter.mysql.entity.TLogLoginExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/

public interface TLogLoginService extends BaseService<TLogLogin,TLogLoginExample>{

    PageParams<TLogLogin> list(PageParams<TLogLogin> pageParams);

    List<TLogLogin> listAll();

    TLogLogin findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TLogLogin bean);


}
