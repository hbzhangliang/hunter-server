package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/20.
 **/
public interface TBizTalentDocService extends BaseService<TBizTalentDoc,TBizTalentDocExample>{

    PageParams<TBizTalentDoc> list(PageParams<TBizTalentDoc> pageParams);

    List<TBizTalentDoc> listAll();

    TBizTalentDoc findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizTalentDoc bean);


    void saveOrUpdate(Long talentId,List<Long> docIds);


    List<Long> getDocIdsByTalentId(Long talentId);
}
