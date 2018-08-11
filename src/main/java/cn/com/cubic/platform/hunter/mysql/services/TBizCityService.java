package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCityExample;
import cn.com.cubic.platform.hunter.mysql.vo.CityTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/8/8.
 **/

public interface TBizCityService extends BaseService<TBizCity,TBizCityExample>{

    PageParams<TBizCity> list(PageParams<TBizCity> pageParams);

    List<TBizCity> listAll();

    TBizCity findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizCity bean);


    //树形结构展示菜单栏
    CityTreeVo tree(@NotEmpty Long id);

    //获取所有的节点数据
    List<CityTreeVo> tree();

    List<Long> getChildrenIds(@NotEmpty Long id);


    void importCitys();

}
