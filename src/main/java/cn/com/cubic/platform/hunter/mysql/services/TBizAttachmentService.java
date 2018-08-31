package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/31.
 **/

public interface TBizAttachmentService  extends BaseService<TBizAttachment,TBizAttachmentExample>{

    List<TBizAttachment> listAll(Long pId);

    TBizAttachment findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizAttachment bean);

}
