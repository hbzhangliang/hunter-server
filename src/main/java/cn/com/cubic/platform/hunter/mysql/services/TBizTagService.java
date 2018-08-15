package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTag;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTagExample;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/15.
 **/

public interface TBizTagService extends BaseService<TBizTag,TBizTagExample>{

    PageParams<TBizTag> list(PageParams<TBizTag> pageParams);

    List<TBizTag> listAll(Long groupId);

    TBizTag findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean delbyGroupIds(List<Long> groupIds);

    Boolean saveOrUpdate(TBizTag bean);


    //树形结构展示菜单栏
    ElTreeVo tree(Long groupId, Long pId);

    //获取所有的节点数据
    List<ElTreeVo> tree(Long groupId);

    List<Long> getChildrenIds(Long pId);

}
