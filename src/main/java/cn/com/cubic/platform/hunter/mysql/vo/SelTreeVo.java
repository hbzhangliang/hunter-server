package cn.com.cubic.platform.hunter.mysql.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/2.
 **/

public class SelTreeVo implements Serializable {
    private String value;
    private String label;
    private int level; //第几层，从0开始
    private List<SelTreeVo> children;

    public SelTreeVo(String value,String label,int level,List<SelTreeVo> children){
        this.value=value;
        this.label=label;
        this.level=level;
        this.children=children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<SelTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<SelTreeVo> children) {
        this.children = children;
    }
}
