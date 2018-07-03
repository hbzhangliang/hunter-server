package cn.com.cubic.platform.hunter.mysql.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liang_zhang on 2017/11/1.
 */
public class PageParams<T> implements Serializable {
    //页码
    private Integer page=1;
    //每页记录数
    private Integer pageSize=10;
    //记录总数
    private Integer totalRows=0;
    //总页数
    private Integer totalPage=1;

    private List<T> data;

    //查询参数放在此
    private T filter;

    //排序的参数
    private String orderBy="id";
    //排序方位 desc asc
    private String direction="desc";

    //
    private  Map<String,Object> params=new HashMap<>(10);

    //临时变量
    private Map<String,Object> tempParams=new HashMap<>(10);

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getTempParams() {
        return tempParams;
    }

    public void setTempParams(Map<String, Object> tempParams) {
        this.tempParams = tempParams;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
