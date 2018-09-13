package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.VShareTalent;
import cn.com.cubic.platform.hunter.mysql.entity.VShareTalentExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/13.
 **/

public interface VShareTalentService extends BaseService<VShareTalent,VShareTalentExample>{

    PageParams<VShareTalent> list(PageParams<VShareTalent> pageParams);

    VShareTalent findById(@NotNull Long id);

}
