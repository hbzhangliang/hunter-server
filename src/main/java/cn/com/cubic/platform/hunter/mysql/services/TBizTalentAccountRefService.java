package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTalentAccountRef;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTalentAccountRefExample;
import cn.com.cubic.platform.hunter.mysql.entity.TSysTeam;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/12.
 **/

public interface TBizTalentAccountRefService  extends BaseService<TBizTalentAccountRef,TBizTalentAccountRefExample>{

    TBizTalentAccountRef findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizTalentAccountRef bean);



    void updateShareData(Long accountId,Long talentId);


}
