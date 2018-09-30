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

    PageParams<TBizTalent> list(PageParams<TBizTalent> pageParams,Boolean ownerFlag);

    List<TBizTalent> listAll();

    TBizTalent findById(@NotNull Long id);

    Boolean fakeDel(List<Long> ids);

    Boolean adminDel(List<Long> ids);

    Boolean saveOrUpdate(TalentVo bean);

    //查询视图信息
    TalentVo findVoById(Long id);

    List<TalentVo> findVoListByIds(List<Long> ids);

}
