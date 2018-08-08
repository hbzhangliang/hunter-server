package cn.com.cubic.platform.hunter.mysql.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/8.
 **/

public class CityTreeVo implements Serializable{

    private Long id;

    private String name;

    private List<CityTreeVo> children;

    public CityTreeVo(Long id,String name,List<CityTreeVo> children){
        this.id=id;
        this.name=name;
        this.children=children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CityTreeVo> children) {
        this.children = children;
    }
}
