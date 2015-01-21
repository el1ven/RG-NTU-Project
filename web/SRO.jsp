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
    String userType = (String) session.getAttribute("userType");
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>SRO</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/dashboard.css" rel="stylesheet">
<link href="bootstrap/css/mycss.css" rel="stylesheet">
<script src="bootstrap/js/jquery.js"></script>
   <script>
    function getCheckForDelete(){
        var deleteIds = document.getElementsByName("deleteId");
        var idArr = [];
        var count = 0;
        for(var i = 0; i < deleteIds.length; i++){
            if(deleteIds[i].checked){
                idArr.push(deleteIds[i].value);
                count++;
            }
        }
        if(count == 0){
            alert('Please Choose One Grant At Least!');
        }else{
            if(confirm('Confirm Delete '+count+' items?')){
                var url = "deleteOfRSO.action?ids="+idArr;//删除和RSO一样
                window.location = url;
            }
        }
    }
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


          <div class="navbar-collapse collapse">
              <div id="nav">
                  <form name="filterForm" action="filter.action" method="post">
                      <ul class="nav-menu clearfix unstyled" style="top:12px;">
                          <li>
                              <input type="text" name="userType" value="<%=userType%>" style="display: none;"/>
                              <select name="filter1">
                                  <option value="fresh">Fresh</option>
                                  <!--<option value="outdate">Outdate</option>-->
                              </select>
                              <select name="filter2">
                                  <option value="All">Month</option>
                                  <option value="01">Jan</option>
                                  <option value="02">Feb</option>
                                  <option value="03">Mar</option>
                                  <option value="04">Apr</option>
                                  <option value="05">May</option>
                                  <option value="06">Jun</option>
                                  <option value="07">Jul</option>
                                  <option value="08">Aug</option>
                                  <option value="09">Sep</option>
                                  <option value="10">Oct</option>
                                  <option value="11">Nov</option>
                                  <option value="12">Dec</option>
                              </select>
                          </li>
                          <li>
                              <select name="filter3">
                                  <option value="All">School</option>
                                  <s:iterator value="schListForFilter">
                                      <option value="<%=request.getAttribute("schName")%>"><%=request.getAttribute("schName")%></option>
                                  </s:iterator>
                              </select>
                          </li>
                          <li>
                              <input type="submit" value="Filter" class="btn-danger btn-xs"/>
                          </li>
                      </ul>
                  </form>

              </div>
          </div>
     </div>
    </div>

    <div class="container-fluid">
    	<div class="row">
        <div id="slidebar" class="col-sm-2 col-md-1 mysidebar">
            <ul class="nav nav-sidebar" id="nav-sidebar">
              <li class="active"><a href="jumpToSRO.jsp" target="_self">Category</a></li>
              <li><a href="jumpToSRO2.jsp" target="_self">Archived</a></li>
              <li><a href="searchSRO1.jsp" target="_self">Search</a></li>
            </ul>
        </div>
      </div>

      <div class="row">
        <div class="col-xs-12 col-sm-10 col-sm-offset-2 col-md-10 col-md-offset-1 main">
          <div class="row">
            <div class="col-md-10"><h2 class="category_shadow">Category</h2></div>
          </div>
          <hr/>
          <div class="table-responsive">
            <table id="table" class="table table-hover" sortLag="-1">
                <thead>
                    <tr>
                      <th><input type="checkbox" onclick="checkAll(this.checked)"></th>
                      <th>
                         <span class="glyphicon glyphicon-edit"></span>
                      </th>
                      <th onClick="mySortInt(2,-1)">
                      <a href="javascript:;" style="text-decoration:none; color:#000;">Id<span class="dropup"><b class="caret"></b></span></a>
                      </th>
                      <th onClick="mySortString(3)">
                      <a href="javascript:;" style="text-decoration:none; color:#000;">Series<span class="dropup"><b class="caret"></b></span></a>
                      </th>
                      <th onClick="mySortString(4)">
                      <a href="javascript:;" style="text-decoration:none; color:#000;">Title<span class="dropup"><b class="caret"></b></span></a>
                      </th>
                      <th onClick="mySortInt(5,-1)">
                      <span class="dropup"><a href="javascript:;" style="color:#000;"><div class="glyphicon glyphicon-user"><b class="caret"></b></div></a></span>
                      </th>
                      <th onClick="mySortInt(6,6">
                      <span class="dropup"><a href="javascript:;" style="color:#000;"><div class="glyphicon glyphicon-calendar" style="margin-left:25px;"><b class="caret"></b></div></a></span>
                      </th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="grantList">
                    <tr>
                      <td><input type="checkbox" name="deleteId" value="<%=request.getAttribute("grantId")%>"/></td>
                      <td data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-pencil getGrantId" style="color:#FF5706;" id="<%=request.getAttribute("grantId")%>"></span></td>
                      <td><%=++i%></td>
                      <td><%=request.getAttribute("grantSeries")%></td>
                      <td><a href="showDetail.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank"><%=request.getAttribute("grantTitle")%></a></td>
                      <td id="<%=request.getAttribute("grantId")%>"  class="getGrantId2" onclick="getDataForSubscribeOfSRO();"><%=request.getAttribute("grantPeopleCount")%></td>
                      <td><%=request.getAttribute("hurryDeadline")%></td>
                      <td id="putIdHere2"></td>
                    </tr>
                    </s:iterator>
                </tbody>
            </table>
            <button onclick="getCheckForDelete();" class="btn btn-danger btn-sm" style="margin-left: -2px;"><span class="glyphicon glyphicon-trash"></span></button>
          </div>
        </div>
      </div>
      
      <div class="row">
        <div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
            Total:&nbsp;<s:property value="rowCount"/>&nbsp;Recordings&nbsp;&nbsp; <!--value的值都通过action映射机制对应action中的属性值-->
            Current:&nbsp;<s:property value="pageNow"/>&nbsp;Page&nbsp;&nbsp;
            <s:url id="url_pre" value="showSRO.action">
                <s:param name="pageNow" value="pageNow-1"></s:param>
            </s:url>

            <s:url id="url_next" value="showSRO.action">
                <s:param name="pageNow" value="pageNow+1"></s:param>
            </s:url>
            <s:a href="%{url_pre}">Previous</s:a>
            <s:a href="%{url_next}">Next</s:a>
        </div><!--分页-->
      </div>
    </div>
    
    <div class="modal fade bs-example-modal-md" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
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
                                        <th>Name</th>
                                        <th>Name</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    <tr>
                                    <td>Cai Jianfei (Assoc Prof)</td>
                                    <td>Erik Cambria (Asst Prof)</td>
                                    </tr>
                                    <tr>
                                    <td>Erik Cambria (Asst Prof)</td>
                                    <td>Chan Syin (Assoc Prof)</td>
                                    </tr>
                                    <tr>
                                    <td>He Ying (Assoc Prof) </td>
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
                                <form class="form-signin" role="form" action="addDeadlineBySRO.action" method="post">
                                    <span class="glyphicon glyphicon-time"></span>
                                    <label style="margin-left:10px;">Set Deadline</label>
                                    <input type="date" class="form-control input-sm" style="margin-bottom:15px;" name="deadline"/>
                                    <input type="text" class="form-control input-sm" style="margin-bottom:15px;" name="description"/>
                                    <input id="putIdHere" type="text" style="height:20px;display: none;" name="grantId" /><!--隐藏value为grantId的input框-->
                                    <input type="submit" class="btn btn-warning btn-md" style="margin-left:10px;" value="Add SRO Deadline" class="form-control"/>
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
