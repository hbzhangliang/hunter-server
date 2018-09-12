package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizShareTalent;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareTalentExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/

public interface TBizShareTalentService extends BaseService<TBizShareTalent,TBizShareTalentExample>{

    PageParams<TBizShareTalent> list(PageParams<TBizShareTalent> pageParams);

    List<TBizShareTalent> listAll();

    TBizShareTalent findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizShareTalent bean);


    Boolean checkShare(Long accountId,Long talentId);


}
