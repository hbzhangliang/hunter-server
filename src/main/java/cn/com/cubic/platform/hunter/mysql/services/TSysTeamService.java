package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TSysTeam;
import cn.com.cubic.platform.hunter.mysql.entity.TSysTeamExample;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/27.
 **/

public interface TSysTeamService extends BaseService<TSysTeam,TSysTeamExample>{

    PageParams<TSysTeam> list(PageParams<TSysTeam> pageParams);

    List<TSysTeam> listAll();

    TSysTeam findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TSysTeam bean);


    //树形结构展示菜单栏
    ElTreeVo tree(@NotEmpty Long id);

    //获取所有的节点数据
    List<ElTreeVo> tree();

    List<Long> getChildrenIds(@NotEmpty Long id);

}
