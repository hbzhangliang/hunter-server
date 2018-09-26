package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCompanyAccountRef;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCompanyAccountRefExample;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTalentAccountRef;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTalentAccountRefExample;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/26.
 **/

public interface TBizCompanyAccountRefService extends BaseService<TBizCompanyAccountRef,TBizCompanyAccountRefExample>{

    TBizCompanyAccountRef findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizCompanyAccountRef bean);



    void updateShareData(Long accountId,Long companyId);


    void updateShareData(List<Long> accountIds,Long companyId);

}
