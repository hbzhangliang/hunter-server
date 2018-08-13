package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizBusiness;
import cn.com.cubic.platform.hunter.mysql.entity.TBizBusinessExample;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/13.
 **/

public interface TBizBusinessService extends BaseService<TBizBusiness,TBizBusinessExample>{

    PageParams<TBizBusiness> list(PageParams<TBizBusiness> pageParams);

    List<TBizBusiness> listAll();

    TBizBusiness findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizBusiness bean);


    //树形结构展示菜单栏
    ElTreeVo tree(@NotEmpty Long id);

    //获取所有的节点数据
    List<ElTreeVo> tree();

    List<Long> getChildrenIds(@NotEmpty Long id);


    void importBusiness();

}
