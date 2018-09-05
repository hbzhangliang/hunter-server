package cn.com.cubic.platform.hunter.mysql.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/8.
 **/

public class ElTreeVo implements Serializable{

    private Long id;

    private String name;

    private Boolean leaf;

    private List<ElTreeVo> children;

    public ElTreeVo(Long id, String name, List<ElTreeVo> children){
        this.id=id;
        this.name=name;
        this.children=children;
        if(children==null||children.size()<1){
            this.leaf=true;
        }
        else {
            this.leaf=false;
        }
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

    public List<ElTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<ElTreeVo> children) {
        this.children = children;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
}
