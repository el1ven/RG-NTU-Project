<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<meta charset="utf-8">
<title>Faculty Home 1</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/dashboard.css" rel="stylesheet">
<link href="bootstrap/css/mycss.css" rel="stylesheet">
<script src="bootstrap/js/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<script src="bootstrap/js/myJavaScript.js"></script>
<script src="bootstrap/js/jquery.js"></script>
<script>
    function getCheckForAdd(){
        var addIds = document.getElementsByName("addId");
        var idArr = [];
        var count = 0;
        for(var i = 0; i < addIds.length; i++){
            if(addIds[i].checked){
                idArr.push(addIds[i].value);
                count++;
            }
        }
        if(count == 0){
            alert('Please Choose One Grant At Least!');
        }else{
            if(confirm('Confirm Add '+count+' items?')){
                var url = "addGrantByFM.action?userName=<%=session.getAttribute("userName")%>&ids="+idArr;
                window.location = url;
            }
        }
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
            <label style="color:#FFF; font-size:18px; margin-top:10px; margin-left:120px;">Hi,<%=session.getAttribute("userName")%></label>
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
                  <!--<form class="navbar-form navbar-right">
                      <input type="text" id="text" class="form-control">
                      <button type="button" class="form-control"><span class="glyphicon glyphicon-search"></span></button>
                  </form>-->

              </div>
          </div>



      </div>
    </div>

    <div class="container-fluid">
        <div class="row"><!-- 侧边导航 -->
          <div id="slidebar" class="col-sm-2 col-md-1 mysidebar">
            <ul class="nav nav-sidebar" id="nav-sidebar">
                <li class="active"><a href="jumpToFM1.jsp" target="_self">Category</a></li>
                <li><a href="jumpToFM2.jsp" target="_self">My List</a></li>
                <li><a href="search.jsp" target="_self">Search</a></li>
            </ul>
          </div>
        </div>

        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <h2 class="sub-header">Category</h2>
                <div class="table-responsive">
                    <table id="tab2" class="table table-hover">
                        <thead>
                        <tr>
                            <!--<th><input type="checkbox" onclick="CheckAll2(this.checked)"></th>-->
                            <th>Opt</th>
                            <th>State</th>
                            <th>Id</th>
                            <th>Seires</th>
                            <th>Title</th>
                            <th>RSO Deadline</th>
                            <th>SRO Deadline</th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="grantList">
                            <tr>
                                <!--<td>
                            <a style="margin-left:-5px;">
                                <button class="btn btn-danger btn-xs" onclick="window.location='addGrantByFM.action?grantId=<%=request.getAttribute("grantId")%>&userName=<%=session.getAttribute("userName")%>'">
                                    <span class="glyphicon glyphicon-heart"></span>
                                </button>
                            </a>
                        </td>-->
                                <td><input type="checkbox" name="addId" value="<%=request.getAttribute("grantId")%>" <% if(request.getAttribute("grantChecked")=="disabled"){%>disabled="disabled"><%}%></td>
                                <td><div style="width: 12px; height: 12px; <% if(request.getAttribute("grantChecked")=="disabled"){%>background-color: red;><%}else{%>background-color: green;<%}%> margin-top: 4px;"></div></td>
                                <td><%=++i%></td>
                                <!--注意这里面的属性是bean的属性名称而不是数据库中的属性名称-->
                                <td><%=request.getAttribute("grantSeries")%></td>
                                <td><a href="showDetail.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank"><%=request.getAttribute("grantTitle")%></a></td>
                                <td><%=request.getAttribute("hurryDeadline")%></td>
                                <td><%=request.getAttribute("hurryDeadline2")%></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                    <br>
                    <a style="margin-left:-5px;">
                        <button class="btn btn-danger btn-md" onclick="getCheckForAdd();">
                            <span class="glyphicon glyphicon-heart"></span>
                        </button>
                    </a>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
                Total:&nbsp;<s:property value="rowCount"/>&nbsp;Recordings&nbsp;&nbsp;
                Current:&nbsp;<s:property value="pageNow"/>&nbsp;Page&nbsp;&nbsp;

                <s:url id="url_pre" value="showFM1.action">
                    <s:param name="pageNow" value="pageNow-1"></s:param>
                </s:url>

                <s:url id="url_next" value="showFM1.action">
                    <s:param name="pageNow" value="pageNow+1"></s:param>
                </s:url>
                <s:a href="%{url_pre}">Previous</s:a>
                <s:a href="%{url_next}">Next</s:a>
            </div>
        </div>
    </div>
</body>
</html>
