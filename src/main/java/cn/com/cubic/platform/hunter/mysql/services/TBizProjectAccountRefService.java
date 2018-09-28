package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizProjectAccountRef;
import cn.com.cubic.platform.hunter.mysql.entity.TBizProjectAccountRefExample;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public interface TBizProjectAccountRefService  extends BaseService<TBizProjectAccountRef,TBizProjectAccountRefExample>{

    TBizProjectAccountRef findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizProjectAccountRef bean);



    void updateShareData(Long accountId,Long projectId);


    void updateShareData(List<Long> accountIds,Long projectId);

}
