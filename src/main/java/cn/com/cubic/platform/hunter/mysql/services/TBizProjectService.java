package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizProject;
import cn.com.cubic.platform.hunter.mysql.entity.TBizProjectExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.hunter.mysql.vo.ProjectVo;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public interface TBizProjectService extends BaseService<TBizProject,TBizProjectExample>{

    PageParams<TBizProject> list(PageParams<TBizProject> pageParams, Boolean ownerFlag);

    List<TBizProject> listAll();

    TBizProject findById(@NotNull Long id);

    Boolean fakeDel(List<Long> ids);

    Boolean adminDel(List<Long> ids);

    Boolean saveOrUpdate(ProjectVo bean);

    ProjectVo findVoById(Long id);

}
