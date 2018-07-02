package cn.com.cubic.platform.hunter.mysql.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liang_zhang on 2017/11/1.
 */
public class PageForm implements Serializable {
    //页码
    private Integer pageNum = 1;
    //每页记录数
    private Integer numPerPage = 10;

    private String orderField;

    private String orderDirection;


    //查询参数
    private Map<String, Object> params = new HashMap<>(10);

    //临时传递参数
    private Map<String, String> tmpParams = new HashMap<String, String>(10);


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, String> getTmpParams() {
        return tmpParams;
    }

    public void setTmpParams(Map<String, String> tmpParams) {
        this.tmpParams = tmpParams;
    }
}
