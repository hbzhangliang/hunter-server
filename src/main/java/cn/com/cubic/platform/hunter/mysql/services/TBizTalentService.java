package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTalent;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTalentExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.TalentVo;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/5.
 **/

public interface TBizTalentService extends BaseService<TBizTalent,TBizTalentExample>{

    PageParams<TBizTalent> list(PageParams<TBizTalent> pageParams);

    List<TBizTalent> listAll();

    TBizTalent findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean delPhysics(List<Long> ids);

    Boolean saveOrUpdate(TalentVo bean);

    //查询视图信息
    TalentVo findVoById(Long id);

}
