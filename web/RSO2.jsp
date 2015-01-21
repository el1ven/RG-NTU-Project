<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 12/10/14
  Time: 6:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% int i = 0;%>
<%
    if(session.getAttribute("isLogin")!="true"){
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>RSO2</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="bootstrap/css/dashboard.css" rel="stylesheet">
    <link href="bootstrap/css/mycss.css" rel="stylesheet">
    <link href="bootstrap/css/jquery-ui.css" rel="stylesheet">
    <script src="bootstrap/js/jquery.js"></script>
    <script>
        $(document).ready(function(){
            $(".getSubscribeData").click(function (e) {

                var rgid = $(e.target).attr('id');
                var params = {grantId: rgid};
                $.ajax({
                    type:"POST",
                    url:"subscribe.action",
                    data:params,
                    dataType:"text json",
                    success:function(data){

                        var str = "";
                        $.each(data.list, function(i, item) {
                            str+=item.name +" "+item.number+"\n";
                        });
                        alert(str);
                    },
                    error:function(data){
                        alert(data);
                    }

                });

            });
        });
    </script>
    <script>
        $(document).ready(function(){
            var rgid;//不能拆成一个动作，不能刚赋完值又立即取值，应该有先后顺序！
            $(".getGrantId").mouseover(function(e) {
                rgid = $(e.target).attr('id');//鼠标移到这个按钮时获取这个rgid属性
            });
            $(".getGrantId").click(function(e2){
                $("#putIdHere").val(rgid);//鼠标点击的时候给这个表单中的input赋值
            });
        });
    </script>
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
      </div>
    </div>

	<div class="container-fluid">
        <div class="row">
          <div id="slidebar" class="col-sm-2 col-md-1 mysidebar">
              <ul class="nav nav-sidebar" id="nav-sidebar">
                <li><a href="jumpToRSO.jsp" target="_self">Category</a></li>
                <li class="active"><a href="jumpToRSO2.jsp" target="_self">Archived</a></li>
                <li><a href="searchRSO1.jsp" target="_self">Search</a></li>
              </ul>
          </div>
        </div>
    
        <div class="row">
            <div class="col-sm-10 col-sm-offset-2 col-md-10 col-md-offset-1 main">
                <h2 class="category_shadow">Archived</h2>
                <hr/>
                <div class="table-responsive">
                    <table id="table" class="table table-hover" sortLag="-1">
                        <thead>
                        <tr>
                            <!--<th><input type="checkbox" onclick="CheckAll(this.checked)"></th>-->
                            <th>
                                <span class="glyphicon glyphicon-edit"></span>
                            </th>
                            <th onClick="mySortInt(2,-1)">
                                <a href="#" style="text-decoration:none; color:#000;">Id<span class="dropup"><b class="caret"></b></span></a>
                            </th>
                            <th onClick="mySortString(3)">
                                <a href="#" style="text-decoration:none; color:#000;">Series<span class="dropup"><b class="caret"></b></span></a>
                            </th>
                            <th onClick="mySortString(4)">
                                <a href="#" style="text-decoration:none; color:#000;">Title<span class="dropup"><b class="caret"></b></span></a>
                            </th>
                            <th onClick="mySortInt(5,-1)">
                                <span class="dropup"><a href="#" style="color:#000;"><div class="glyphicon glyphicon-user"><b class="caret"></b></div></a></span>
                            </th>
                            <th onClick="mySortInt(6,6)">
                                <span class="dropup"><a href="#" style="color:#000;"><div class="glyphicon glyphicon-calendar" style="margin-left:25px;"><b class="caret"></b></div></a></span>
                            </th>
                            <th>
                                MarkDown
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="grantList">
                            <tr>
                                <!--<td><input type="checkbox" name="deleteId" value="<%=request.getAttribute("grantId")%>"/></td>-->
                                <td data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-pencil getGrantId" style="color:#A007FF" id="<%=request.getAttribute("grantId")%>"></span></td>
                                <td><%=++i%></td>
                                <td><%=request.getAttribute("grantSeries")%></td>
                                <td><a href="showDetail2.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank"><%=request.getAttribute("grantTitle")%></a></td>
                                <td class="getSubscribeData" id="<%=request.getAttribute("grantId")%>"><%=request.getAttribute("grantPeopleCount")%></td>
                                <td><%=request.getAttribute("hurryDeadline")%></td>
                                <td><%=request.getAttribute("markdown")%></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    	<div class="row">
            <div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
                Total:&nbsp;<s:property value="rowCount"/>&nbsp;Recordings&nbsp;&nbsp; <!--value的值都通过action映射机制对应action中的属性值-->
                Current:&nbsp;<s:property value="pageNow"/>&nbsp;Page&nbsp;&nbsp;

                <s:url id="url_pre" value="showRSO2.action">
                    <s:param name="pageNow" value="pageNow-1"></s:param>
                </s:url>

                <s:url id="url_next" value="showRSO2.action">
                    <s:param name="pageNow" value="pageNow+1"></s:param>
                </s:url>
                <s:a href="%{url_pre}">Previous</s:a>
                <s:a href="%{url_next}">Next</s:a>
            </div>
        </div>
    </div>

    <!--<div class="modal fade bs-example-modal-md" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-md">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                    <div class="container" style="margin-left:40px;">
                      <div class="row">
                        <div class="col-md-5">
                            <form class="form-signin" role="form">
                                <table id="table" class="table table-bordered" sortLag="-1">
                                  <thead>
                                    <tr>
                                        <th>School</th>
                                        <th>number</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    <tr>
                                    <td>SCE</td>
                                    <td>2</td>
                                    </tr>
                                    <tr>
                                    <td>MAE</td>
                                    <td>1</td>
                                    </tr>
                                    <tr>
                                    <td>EEE</td>
                                    <td>1</td>
                                    </tr>
                                  </tbody>
                                </table>
                            </form>
                        </div>
                      </div>
                    </div>
                </div>
            </div>
        </div>
    </div>-->

    <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true"><!-- 设置时间模块 -->
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                    <div class="container" style="margin-left:40px;">
                        <div class="row">
                            <div class="col-sm-2">
                                <form class="form-signin" role="form" action="addMark.action" method="post">
                                    <label style="margin-left:10px;">Set MarkDown</label>
                                    <input type="text" class="form-control input-sm" style="margin-bottom:15px;" name="markDown">
                                    <input id="putIdHere" type="text" style="height:20px; display: none;" name="rgidOfStr" /><!--隐藏value为grantId的input框-->
                                    <input class="btn btn-warning btn-md"  type="submit" value="Add MarkDown By RSO"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="bootstrap/js/jquery-2.1.1.min.js"></script>
    <script src="bootstrap/js/jquery-ui.js"></script>
    <script src="bootstrap/js/myJavaScript.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>
