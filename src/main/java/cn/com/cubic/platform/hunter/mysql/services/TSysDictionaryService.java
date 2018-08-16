package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TSysDictionary;
import cn.com.cubic.platform.hunter.mysql.entity.TSysDictionaryExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/16.
 **/

public interface TSysDictionaryService extends BaseService<TSysDictionary,TSysDictionaryExample>{

    PageParams<TSysDictionary> list(PageParams<TSysDictionary> pageParams);

    List<TSysDictionary> list(Long pId);

    List<TSysDictionary> listAll();

    TSysDictionary findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TSysDictionary bean);

}
