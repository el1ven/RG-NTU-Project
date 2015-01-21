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
    <title>SRO</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="bootstrap/css/dashboard.css" rel="stylesheet">
    <link href="bootstrap/css/mycss.css" rel="stylesheet">
    <link href="bootstrap/css/jquery-ui.css" rel="stylesheet">
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
            <ul class="nav-menu clearfix unstyled">
                <li>
                  <a href="#" class="three-d">Filter<span class="three-d-box"><span class="front">Filter
                      </span><span class="back">Filter</span></span>
                  </a>
                </li>
                
                <li>
                    <a href="#" class="three-d">Series
                        <span class="three-d-box"><span class="front">Series</span><span class="back">Series</span></span>
                    </a>
                    <ul class="clearfix unstyled drop-menu">
                        <li>
                            <a href="#" class="three-d" onClick="seriesFilter(this,1)">Architecture
                                <span class="three-d-box"><span class="front">Architecture</span><span class="back">Architecture</span></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="three-d" onClick="seriesFilter(this,1)">Biology
                                <span class="three-d-box"><span class="front">Biology</span><span class="back">Biology</span></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="three-d" onClick="seriesFilter(this,1)">Business
                                <span class="three-d-box"><span class="front">Business</span><span class="back">Business</span></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="three-d" onClick="seriesFilter(this,1)">Computer
                                <span class="three-d-box"><span class="front">Computer</span><span class="back">Computer</span></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="three-d" onClick="seriesFilter(this,1)">Chemistry  
                                <span class="three-d-box"><span class="front">Chemistry</span><span class="back">Chemistry</span></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="three-d" onClick="seriesFilter(this,1)">Psychology
                                <span class="three-d-box"><span class="front">Psychology</span><span class="back">Psychology</span></span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="three-d" onClick="seriesFilter(this,1)">Physics
                                <span class="three-d-box"><span class="front">Physics</span><span class="back">Physics</span></span>
                            </a>
                        </li>
                    </ul>
                </li>
                
                <li>
                  <a href="#" class="three-d">
                      Month
                    <span class="three-d-box"><span class="front">Month</span><span class="back">Month</span></span>
                    </a>
                    <ul class="clearfix unstyled drop-menu" style="width:130px;">
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Dec
                                <span class="three-d-box"><span class="front">Dec</span><span class="back">Dec</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Nov
                                <span class="three-d-box"><span class="front">Nov</span><span class="back">Nov</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Oct
                                <span class="three-d-box"><span class="front">Oct</span><span class="back">Oct</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Sep
                                <span class="three-d-box"><span class="front">Sep</span><span class="back">Sep</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Aug
                                <span class="three-d-box"><span class="front">Aug</span><span class="back">Aug</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Jul
                                <span class="three-d-box"><span class="front">Jul</span><span class="back">Jul</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Jun
                                <span class="three-d-box"><span class="front">Jun</span><span class="back">Jun</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">May
                                <span class="three-d-box"><span class="front">May</span><span class="back">May</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Apr
                                <span class="three-d-box"><span class="front">Apr</span><span class="back">Apr</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Mar
                                <span class="three-d-box"><span class="front">Mar</span><span class="back">Mar</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Feb
                                <span class="three-d-box"><span class="front">Feb</span><span class="back">Feb</span></span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="three-d" onClick="dateFilter(this,3)">Jan
                                <span class="three-d-box"><span class="front">Jan</span><span class="back">Jan</span></span>
                          </a>
                        </li>
                    </ul>
                </li>
                
                <li>
                  <a href="#" class="three-d">
                    School
                    <span class="three-d-box"><span class="front">School</span><span class="back">School</span></span>
                  </a>
                </li>
            </ul>
          </div>
          <form class="navbar-form navbar-right">
            <input type="text" id="text" class="form-control">
            <button type="button" class="form-control"><span class="glyphicon glyphicon-search"></span></button>
          </form>
        </div>
      </div>
    </div>
    
    <div class="container-fluid">
        <div class="row">
          <span id="icon-menu">
              <i class="icon-menu"></i>
          </span>
          <div id="slidebar" class="col-sm-2 col-md-1 mysidebar">
              <ul class="nav nav-sidebar" id="nav-sidebar">
                <li><a href="jumpToSRO.jsp" target="_self">Category</a></li>
                <li class="active"><a href="jumpToSRO2.jsp" target="_self">Archived</a></li>
              </ul>
          </div>
        </div>
        <div class="row">
            <div class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                <h2 class="category_shadow">Category</h2>
                <hr/>
                <div class="table-responsive">
                  <table id="table" class="table table-hover" sortLag="-1">
                    <thead>
                      <tr>
                        <th onClick="mySortInt(0,-1)">
                        <a href="#" style="text-decoration:none; color:#000;">Id<span class="dropup"><b class="caret"></b></span></a>
                        </th>
                        <th onClick="mySortString(1)">
                        <a href="#" style="text-decoration:none; color:#000;">Series<span class="dropup"><b class="caret"></b></span></a>
                        </th>
                        <th onClick="mySortString(2)">
                        <a href="#" style="text-decoration:none; color:#000;">Title<span class="dropup"><b class="caret"></b></span></a>
                        </th>
                        <th onClick="mySortInt(3,3)">
                        <span class="dropup"><a href="#" style="color:#000;"><div class="glyphicon glyphicon-calendar" style="margin-left:25px;"><b class="caret"></b></div></a></span>
                        </th>
                        <th>
                        <span class="glyphicon glyphicon-edit"></span>
                        </th>
                        <th><input type="checkbox" onclick="CheckAll(this.checked)"></th>
                      </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="grantList">
                        <tr>
                          <td><%=++i%></td>
                          <td><%=request.getAttribute("grantSeries")%></td>
                          <td><a href="showDetail2.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank"><%=request.getAttribute("grantTitle")%></a></td>
                          <td data-toggle="modal" data-target=".bs-example-modal-md"><%=request.getAttribute("grantPeopleCount")%></td>
                          <td>未知的deadline</td>
                          <td><a href="form_modify.html" target="_blank" style="color:#60F;" ><span class="glyphicon glyphicon-pencil"></span></a></td>
                          <td><input type="checkbox" name="deleteId" value="<%=request.getAttribute("grantId")%>"/></td>
                        </tr>
                        </s:iterator>
                    </tbody>
                    </table>
                </div>
            </div>
        </div>
            
        <div class="row">
            <div class="col-sm-4 col-sm-offset-5 col-md-4 col-md-offset-5">
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
    <script src="bootstrap/js/jquery-2.1.1.min.js"></script>
	<script src="bootstrap/js/jquery-ui.js"></script>
    <script src="bootstrap/js/myJavaScript.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>
