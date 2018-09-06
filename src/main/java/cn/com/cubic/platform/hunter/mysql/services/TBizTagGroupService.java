package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTagGroup;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTagGroupExample;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/15.
 **/

public interface TBizTagGroupService extends BaseService<TBizTagGroup,TBizTagGroupExample>{

    PageParams<TBizTagGroup> list(PageParams<TBizTagGroup> pageParams);

    List<TBizTagGroup> listAll();

    TBizTagGroup findById(@NotNull Long id);

    TBizTagGroup findByCode(@NotNull String groupCode);

    Boolean delTx(List<Long> ids);

    Boolean saveOrUpdate(TBizTagGroup bean);

    //获取所有的节点数据
    List<ElTreeVo> tree();
}
