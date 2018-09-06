package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.services.TBizTagGroupService;
import cn.com.cubic.platform.hunter.mysql.services.TBizTagService;
import cn.com.cubic.platform.hunter.mysql.vo.ElTreeVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.cubic.platform.utils.Exception.HunterException;
import cn.com.cubic.platform.utils.UtilHelper;
import cn.com.cubic.platform.utils.global.GlobalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/15.
 **/
@Service
public class TBizTagGroupServiceImpl  extends BaseServiceImpl<TBizTagGroup,TBizTagGroupExample> implements TBizTagGroupService {

    private final static Logger log = LoggerFactory.getLogger(TBizTagGroupServiceImpl.class);

    @Autowired
    private TBizTagService tagService;

    @Override
    public PageParams<TBizTagGroup> list(PageParams<TBizTagGroup> pageParams) {
        //查询参数
        TBizTagGroupExample example=new TBizTagGroupExample();
        //排序
        String strOrder=String.format("%s %s", UtilHelper.camelToUnderline(pageParams.getOrderBy()),pageParams.getDirection());
        example.setOrderByClause(strOrder);
        return this.listPage(example,pageParams);
    }

    @Override
    public List<TBizTagGroup> listAll() {
        TBizTagGroupExample example=new TBizTagGroupExample();
        example.setOrderByClause("seq is null,seq");
        return this.selectByExample(example);
    }

    @Override
    public TBizTagGroup findById(Long id) {
        TBizTagGroupExample example = new TBizTagGroupExample();
        example.createCriteria().andIdEqualTo(id);
        List<TBizTagGroup> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }


    @Override
    public TBizTagGroup findByCode(String groupCode) {
        TBizTagGroupExample example = new TBizTagGroupExample();
        example.createCriteria().andCodeEqualTo(groupCode);
        List<TBizTagGroup> list = this.selectByExample(example);
        if (null != list && 1 != list.size()) {
            throw new HunterException("查询错误");
        }
        return list.get(0);
    }

    /**
     * 包含事务
     * @param ids
     * @return
     */
    @Override
    public Boolean delTx(List<Long> ids) {
        //删除子表
        tagService.delbyGroupIds(ids);

        TBizTagGroupExample example = new TBizTagGroupExample();
        example.createCriteria().andIdIn(ids);
        this.deleteByExample(example);
        return true;
    }

    @Override
    public Boolean saveOrUpdate(TBizTagGroup bean) {
        Date dt=new Date();
        TSysAccount user=(TSysAccount) GlobalHolder.get().get("account");
        if (null != bean.getId()) {
            TBizTagGroupExample example = new TBizTagGroupExample();
            example.createCriteria().andIdEqualTo(bean.getId());
            bean.setModifyBy(user.getName());
            bean.setModifyTime(dt);
            this.updateByExampleSelective(bean, example);
        } else {
            bean.setCreateBy(user.getName());
            bean.setCreateTime(dt);
            this.insert(bean);
        }
        return true;
    }

    /**
     * 没有子节点
     * @return
     */
    @Override
    public List<ElTreeVo> tree() {
        List<TBizTagGroup> list=this.listAll();
        if(null!=list&&!list.isEmpty()){
            List<ElTreeVo> treeVos=new ArrayList<>(10);
            for(TBizTagGroup item:list){
                treeVos.add(new ElTreeVo(item.getId(),item.getName(),null));
            }
            return treeVos;
        }
        return null;
    }
}
