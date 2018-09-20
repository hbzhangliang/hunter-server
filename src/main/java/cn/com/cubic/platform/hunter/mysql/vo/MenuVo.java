package cn.com.cubic.platform.hunter.mysql.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/20.
 **/

public class MenuVo  implements Serializable{

    private String id;
    private String path;
    private String icon;
    private String name;
    private List<MenuVo> children;

    public MenuVo(String id,String icon,String name){
        this.id=id;
        this.path=id;
        this.icon=icon;
        this.name=name;
    }


    public MenuVo(String id,String icon,String name,List<MenuVo> children){
        this.id=id;
        this.path=id;
        this.icon=icon;
        this.name=name;
        this.children=children;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }
}
