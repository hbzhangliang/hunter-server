package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizRecordWork;
import cn.com.cubic.platform.hunter.mysql.entity.TBizRecordWorkExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/7.
 **/

public interface TBizRecordWorkService  extends BaseService<TBizRecordWork,TBizRecordWorkExample>{
    PageParams<TBizRecordWork> list(PageParams<TBizRecordWork> pageParams);

    List<TBizRecordWork> listAll();

    TBizRecordWork findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizRecordWork bean);

}
