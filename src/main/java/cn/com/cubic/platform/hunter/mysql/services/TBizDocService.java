package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TBizDocExample;
import cn.com.cubic.platform.hunter.mysql.vo.*;
import com.sun.istack.internal.NotNull;

import javax.print.Doc;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/31.
 **/

public interface TBizDocService extends BaseService<TBizDoc,TBizDocExample>{

    PageParams<TBizDoc> list(PageParams<TBizDoc> pageParams);

    List<TBizDoc> listAll();

    TBizDoc findById(@NotNull Long id);

    DocVo findDocVo(Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizDoc bean);


    Boolean saveTx(DocVo bean);


    List<ElTreeVo> tree(List<String> types);



    List<SelTreeVo> allTree();



    //提供给其他页面共享调用
    List<SelTreeVo> allShareTree();


    /**
     * 查询个人文件夹
     * @param ownerId
     * @return
     */
    List<TBizDoc> docListByOwner(Long ownerId);


    List<TBizDoc> docListByOwner(Long ownerId,String type);


    /**
     * 查询某个人有权限访问的分享的文件夹
     * @param accountId
     * @return
     */
    List<TBizDoc> docListByShare(Long accountId);

    List<TBizDoc> docListByShare(Long accountId,String type);
    /**
     * 获取某人的目录
     * @param accountId
     * @return
     */
    MenuVo listMenu(Long accountId);



    List<ElTreeVo> listDocByAccountId(Long accountId,String type);

}
