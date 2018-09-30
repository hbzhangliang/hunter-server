package cn.com.cubic.platform.utils;

import cn.com.cubic.platform.hunter.mysql.entity.TBizBusiness;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCareer;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCity;
import cn.com.cubic.platform.hunter.mysql.entity.TBizTag;
import cn.com.cubic.platform.hunter.mysql.services.TBizBusinessService;
import cn.com.cubic.platform.hunter.mysql.services.TBizCareerService;
import cn.com.cubic.platform.hunter.mysql.services.TBizCityService;
import cn.com.cubic.platform.hunter.mysql.services.TBizTagService;
import cn.com.cubic.platform.hunter.mysql.vo.TalentVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/9.
 **/
@Component
public class ComServers {

    private static final Logger log = LoggerFactory.getLogger(ComServers.class);

    @Autowired
    private TBizCityService cityService;

    @Autowired
    private TBizBusinessService businessService;

    @Autowired
    private TBizCareerService careerService;

    @Autowired
    private TBizTagService tagService;

    public List<TBizCity> allCityList(){
        return cityService.listAll();
    }

    public List<TBizBusiness> allBusinessList(){
        return businessService.listAll();
    }

    public List<TBizCareer> allCareerList(){
        return careerService.listAll();
    }

    public List<TBizTag> allTalentTagList(){
        String tagGroupCode="talent";
        return tagService.listAll(tagGroupCode);
    }

    /**
     * 根据id获取城市数据
     * @param id
     * @return
     */
    public String getCityName(Long id){
        List<TBizCity> list=this.allCityList();
        if(!(null==id||null==list||list.size()<1)){
            for(TBizCity item:list){
                if(item.getId().equals(id)){
                    return item.getName();
                }
            }
        }
        return null;
    }



    public String getCityNames(List<Long> ids){
        if(null!=ids&&ids.size()>0){
            String result="";
            for(Long id:ids){
                String name=this.getCityName(id);
                if(name!=null){
                    result+=name+",";
                }
            }
            return result;
        }
        return null;
    }


    /**
     * 获取行业名称
     * @param id
     * @return
     */
    public String getBusinessName(Long id){
        List<TBizBusiness> list=this.allBusinessList();
        if(!(null==id||null==list||list.size()<1)){
            for(TBizBusiness item:list){
                if(item.getId().equals(id)){
                    return item.getName();
                }
            }
        }
        return null;
    }


    public String getBusinesssNames(List<Long> ids){
        if(null!=ids&&ids.size()>0){
            String result="";
            for(Long id:ids){
                String name=this.getBusinessName(id);
                if(name!=null){
                    result+=name+",";
                }
            }
            return result;
        }
        return null;
    }

    public List<Long> getSplitIds(String ids){
        if(StringUtils.isNotEmpty(ids)){
            List<Long> result=new ArrayList<>(10);
            for(String p:ids.split(",")){
                result.add(Long.valueOf(p));
            }
            return result;
        }
        return null;
    }



    /**
     * 获取职能名称
     * @param id
     * @return
     */
    public String getCareerName(Long id){
        List<TBizCareer> list=this.allCareerList();
        if(!(null==id||null==list||list.size()<1)){
            for(TBizCareer item:list){
                if(item.getId().equals(id)){
                    return item.getName();
                }
            }
        }
        return null;
    }


    public String getCareerNames(List<Long> ids){
        if(null!=ids&&ids.size()>0){
            String result="";
            for(Long id:ids){
                String name=this.getCareerName(id);
                if(name!=null){
                    result+=name+",";
                }
            }
            return result;
        }
        return null;

    }


    public String getTalentNames(List<TalentVo> list){
        if(null!=list&&list.size()>0){
            String result="";
            for(TalentVo item:list){
                result+=item.getName()+",";
            }
            return result;
        }
        return null;
    }



    public String getTagName(Long id){
        List<TBizTag> list=this.allTalentTagList();
        if(!(null==id||null==list||list.size()<1)){
            for(TBizTag item:list){
                if(item.getId().equals(id)){
                    return item.getName();
                }
            }
        }
        return null;
    }



    public String getTagNames(List<Long> ids){
        if(null!=ids&&ids.size()>0){
            String result="";
            for(Long id:ids){
                String name=this.getTagName(id);
                if(name!=null){
                    result+=name+",";
                }
            }
            return result;
        }
        return null;
    }



}
