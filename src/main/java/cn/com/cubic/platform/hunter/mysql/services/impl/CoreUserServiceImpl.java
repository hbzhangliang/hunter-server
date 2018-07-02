package cn.com.cubic.platform.hunter.mysql.services.impl;

import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.entity.CoreUserExample;
import cn.com.cubic.platform.hunter.mysql.services.CoreUserService;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

@Service
public class CoreUserServiceImpl extends BaseServiceImpl<CoreUser,CoreUserExample> implements CoreUserService{

    private final static Logger log = LoggerFactory.getLogger(CoreUserServiceImpl.class);

    @Override
    public BaseResponse<PageParams<CoreUser>> list(PageParams pageParams) {
        CoreUserExample example = new CoreUserExample();
        Map<String, Object> map = pageParams.getParams();
        if (null != map && null != map.get("name")) {
            example.createCriteria().andNameLike(map.get("name").toString());
        }
        if(StringUtils.isNotEmpty(pageParams.getOrderField())){
            if(StringUtils.isNotBlank(pageParams.getOrderDirection())&&pageParams.getOrderDirection().equals("desc")){
                example.setOrderByClause(pageParams.getOrderField()+" desc");
            }
            else {
                example.setOrderByClause(pageParams.getOrderField());
            }
        }
        return new BaseResponse<>("查询数据成功", this.listPage(example, pageParams));
    }

    @Override
    public BaseResponse<CoreUser> findById(Long id) {
        CoreUserExample example = new CoreUserExample();
        example.createCriteria().andIdEqualTo(id);
        List<CoreUser> list = this.selectByExample(example);
        if (null != list && 1 == list.size()) {
            return new BaseResponse<>("查询数据成功", list.get(0));
        }
        return new BaseResponse<>("未查询到数据或多条数据", null);
    }

    @Override
    public BaseResponse<Boolean> del(List<Long> ids) {
        try {
            CoreUserExample example = new CoreUserExample();
            example.createCriteria().andIdIn(ids);
            this.deleteByExample(example);
            return new BaseResponse<>("删除数据成功",true);
        }
        catch (Exception e){
            e.printStackTrace();
            return new BaseResponse<>("删除数据失败",false);
        }
    }

    @Override
    public BaseResponse<Boolean> saveOrUpdate(CoreUser coreUser) {
        if (null != coreUser.getId()) {
            CoreUserExample example = new CoreUserExample();
            example.createCriteria().andIdEqualTo(coreUser.getId());
            this.updateByExampleSelective(coreUser, example);
        } else {
            this.insert(coreUser);
        }
        return new BaseResponse<>("保存或更新数据成功", true);
    }
}
