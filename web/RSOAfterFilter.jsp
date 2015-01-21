<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 2/1/15
  Time: 3:09 PM
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
    String userType = (String) session.getAttribute("userType");
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>RSO-AfterFilter</title>
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
                    var url = "deleteOfRSO.action?ids="+idArr;
                    window.location = url;
                }
            }
        }
    </script>
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

                        //var obj = $.parseJSON(data);
                        //var obj = eval("("+data+")");
                        //var val = obj.result;  //result是和action中定义的result变量的get方法对应的
                        var str = "";
                        $.each(data.list, function(i, item) {
                            str+=item.name +" "+item.number+"\n";
                        });
                        alert(str);
                    },
                    error:function(data){
                        alert(data);
                        /*错误调试信息*/
                        //alert(XMLHttpRequest.status);
                        //alert(XMLHttpRequest.readyState);
                        //alert(textStatus);
                    }

                });

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

<div class="container-fluid">
    <div class="row">
        <div id="slidebar" class="col-sm-2 col-md-1 mysidebar">
            <ul class="nav nav-sidebar" id="nav-sidebar">
                <li class="active"><a href="jumpToRSO.jsp" target="_self">Category</a></li>
                <li><a href="jumpToRSO2.jsp" target="_self">Archived</a></li>
                <li><a href="searchRSO1.jsp" target="_self">Search</a></li>
            </ul>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 col-sm-10 col-sm-offset-2 col-md-10 col-md-offset-1 main">
            <div class="row">
                <div class="col-md-9 col-sm-7 col-xs-4">
                    <h2>RSO-AfterFilter</h2>
                </div>
            </div>
            <hr/>
            <div class="table-responsive">
                <table id="table" class="table table-hover" sortLag="-1">
                    <thead>
                    <tr>
                        <th>
                            Opt
                        </th>
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
                        <th onClick="mySortString(5)">
                            <span class="dropup"><a href="javascript:;" style="color:#000;"><div class="glyphicon glyphicon-home"><b class="caret"></b></div></a></span>
                        </th>
                        <th onClick="mySortInt(6,-1)">
                            <span class="dropup"><a href="javascript:;" style="color:#000;"><div class="glyphicon glyphicon-user"><b class="caret"></b></div></a></span>
                        </th>
                        <th onClick="mySortInt(7,7)">
                            <span class="dropup"><a href="javascript:;" style="color:#000;"><div class="glyphicon glyphicon-calendar" style="margin-left:25px;"><b class="caret"></b></div></a></span>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="grantListAfterFilter">
                        <tr>
                            <td><input type="checkbox" name="deleteId" value="<%=request.getAttribute("grantId")%>"/></td>
                            <td>
                                <a href="showModify.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank" style="color:#60F;">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </a>
                            </td>
                            <td><%=++i%></td>
                            <td><%=request.getAttribute("grantSeries")%></td>
                            <td><a href="showDetail.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank"><%=request.getAttribute("grantTitle")%></a></td>
                            <td><%=request.getAttribute("grantSchool")%></td>
                            <td class="getSubscribeData" id="<%=request.getAttribute("grantId")%>"><%=request.getAttribute("grantPeopleCount")%></td>
                            <td><%=request.getAttribute("hurryDeadline")%></td>

                            <input style="visibility: hidden;" id="getGrantId" value="<%=request.getAttribute("grantId")%>"/>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
                <button onclick="getCheckForDelete();" class="btn btn-danger btn-sm" style="margin-left: -2px;"><span class="glyphicon glyphicon-trash"></span></button>
            </div>
        </div>
    </div>


</div>

<script src="bootstrap/js/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/myJavaScript.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
</div>
</body>
</html>
