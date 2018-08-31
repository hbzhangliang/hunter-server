package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TBizDocExample;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/31.
 **/

public interface TBizDocService extends BaseService<TBizDoc,TBizDocExample>{

    PageParams<TBizDoc> list(PageParams<TBizDoc> pageParams);

    List<TBizDoc> listAll();

    TBizDoc findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizDoc bean);


    List<ElTreeVo> tree(List<String> types);

}
