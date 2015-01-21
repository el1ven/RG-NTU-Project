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
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>SRO2</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/dashboard.css" rel="stylesheet">
<link href="bootstrap/css/mycss.css" rel="stylesheet">
<link href="bootstrap/css/jquery-ui.css" rel="stylesheet">
<script src="bootstrap/js/jquery.js"></script>
    <script>
        $(document).ready(function(){
            var rgid;//不能拆成一个动作，不能刚赋完值又立即取值，应该有先后顺序！
            $(".getGrantId").mouseover(function(e) {
                rgid = $(e.target).attr('id');//鼠标移到这个按钮时获取这个rgid属性
            });
            $(".getGrantId").click(function(e2){
                $("#putIdHere").val(rgid);//鼠标点击的时候给这个表单中的input赋值
            });

            getId2();

        });
    </script>
    <script>

        function getId2() {

            var rgid;//不能拆成一个动作，不能刚赋完值又立即取值，应该有先后顺序！
            $(".getGrantId2").mouseover(function(e) {
                rgid = $(e.target).attr('id');//鼠标移到这个按钮时获取这个rgid属性
                $("#putIdHere2").val(rgid);//鼠标点击的时候给这个表单中的input赋值
            });
        }

        function getDataForSubscribeOfSRO(){

            var argu2 = {rgid: $("#putIdHere2").val(), info: "getSubscribeDataBySRO"};
            $.ajax({
                type:"POST",
                url:"ajaxSubscribeDataBySRO.action",
                data:argu2,
                dataType:"text json",
                success:function(data){
                    var str = "";
                    $.each(data.list, function(i, item) {
                        str+=item.schName +": "+item.namesForSRO+"\n";
                    });
                    alert(str);
                },
                error:function(data){
                    alert(data);
                }
            });

        }
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
                <li><a href="jumpToSRO.jsp" target="_self">Category</a></li>
                <li class="active"><a href="jumpToSRO2.jsp" target="_self">Archived</a></li>
                <li><a href="searchSRO1.jsp" target="_self">Search</a></li>

              </ul>
          </div>
        </div>
        <div class="row">
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-1 main">
                <h2 class="category_shadow">Archived</h2>
                <hr/>
                <div class="table-responsive">
                  <table id="table" class="table table-hover" sortLag="-1">
                    <thead>
                      <tr>
                        <th><input type="checkbox" onclick="CheckAll(this.checked)"></th>
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
                          <td><input type="checkbox" name="deleteId" value="<%=request.getAttribute("grantId")%>"/></td>
                          <td data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-pencil getGrantId" style="color:#A007FF" id="<%=request.getAttribute("grantId")%>"></span></td>
                          <td><%=++i%></td>
                          <td><%=request.getAttribute("grantSeries")%></td>
                          <td><a href="showDetail2.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank"><%=request.getAttribute("grantTitle")%></a></td>
                          <td id="<%=request.getAttribute("grantId")%>"  class="getGrantId2" onclick="getDataForSubscribeOfSRO();"><%=request.getAttribute("grantPeopleCount")%></td>
                          <td><%=request.getAttribute("hurryDeadline2")%></td>
                          <td><%=request.getAttribute("markdown2")%></td>
                          <td id="putIdHere2"></td>

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

                <s:url id="url_pre" value="showSRO2.action">
                    <s:param name="pageNow" value="pageNow-1"></s:param>
                </s:url>

                <s:url id="url_next" value="showSRO2.action">
                    <s:param name="pageNow" value="pageNow+1"></s:param>
                </s:url>
                <s:a href="%{url_pre}">Previous</s:a>
                <s:a href="%{url_next}">Next</s:a>
            </div>
        </div>
    </div>
    


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
                                    <input id="putIdHere" type="text" style="height:20px;display: none;" name="rgidOfStr" /><!--隐藏value为grantId的input框-->
                                    <input type="submit" class="btn btn-warning btn-md" style=" margin-left:-5px;" value="Add MarkDown By SRO"/>
                                    </a>
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
