package cn.com.cubic.platform.hunter.mysql.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liang_zhang on 2017/11/1.
 */
public class PageParams<T> implements Serializable {

    private Integer pageNum=1;
    //当前页码
    private Integer currentPage=1;

    //每页记录数
    private Integer numPerPage=10;

    //总页码
    private Integer pageNumShown;

    //总记录数
    private Integer totalCount;

    private String orderField="id";

    private String orderDirection="asc";

    private List<T> data;

    private T bean;

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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getPageNumShown() {
        return pageNumShown;
    }

    public void setPageNumShown(Integer pageNumShown) {
        this.pageNumShown = pageNumShown;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
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
