<%--
  Created by IntelliJ IDEA.
  User: hbzhangliang
  Date: 2018/6/26
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
</head>
<body>




<form id="pagerForm" method="post" action="#rel#">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="pageSize" value="${obj.data.pageSize}" />
    <input type="hidden" name="orderField" value="${obj.data.orderField}" />
    <input type="hidden" name="orderDirection" value="${obj.data.orderDirection}" />
</form>

<div class="pageHeader">
    <form id="mySearchForm" rel="pagerForm" onsubmit="return navTabSearch(this);" action="${base}/test/list.htm" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>我的客户：</label>
                    <input type="text" name="name" value=""/>
                </li>
            </ul>
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
                    <li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="demo_page4.html" target="navTab"><span>添加</span></a></li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" href="demo/common/ajaxDone.html" class="delete"><span>批量删除默认方式</span></a></li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="demo/common/ajaxDone.html" class="delete"><span>批量删除逗号分隔</span></a></li>
            <li><a class="edit" href="demo_page4.html?uid={sid_user}" target="navTab" warn="请选择一个用户"><span>修改</span></a></li>
            <li class="line">line</li>
            <li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
            <li><a class="icon" href="javascript:myExport('demo/common/dwz-team.xls')"><span>自定义导出</span></a></li>
            <li><a title="选中id跳出DWZ页面打印?" target="selectedBlank" rel="ids" postType="string" href="demo/common/ajaxDone.html" class="icon"><span>选中跳出DWZ</span></a></li>
        </ul>
    </div>

    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="10%">id</th>
            <th width="10%" orderField="code" class="${obj.data.orderDirection}">code${obj.data.orderDirection}</th>
            <th width="10%" orderField="name" class="${obj.data.orderDirection}">name</th>
            <th width="10%">psd</th>
            <th width="10%">type</th>
            <th width="15%">status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${obj.data.data}" var="bean" varStatus="i">
            <tr id="tr_${bean.id}">
                <td><input class="chk" type="checkbox" id="chk_${bean.id}"/></td>
                <td>${bean.code}</td>
                <td>${bean.name}</td>
                <td>${bean.psd}</td>
                <td>${bean.type}</td>
                <td>${bean.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="panelBar">
        <div class="pages">
            <span>显示</span>
            <select class="combox" name="pageSize" onchange="navTabPageBreak({pageSize:this.value})">
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
                <option value="200">200</option>
            </select>
            <span>条，共${obj.data.totalRows}条</span>
        </div>

        <div class="pagination" targetType="navTab" totalCount="${obj.data.totalRows}" pageSize="${obj.data.pageSize}" pageNumShown="${obj.data.totalPage}" currentPage="${obj.data.currentPage}"></div>

    </div>
</div>


</body>
</html>
