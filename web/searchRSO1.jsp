<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 8/1/15
  Time: 4:39 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
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
    <title>searchRSO1</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="bootstrap/css/dashboard.css" rel="stylesheet">
    <link href="bootstrap/css/mycss.css" rel="stylesheet">
    <script src="bootstrap/js/jquery.js"></script>
    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script>

        var userType = '<%=session.getAttribute("userType")%>';
        var searchType = "";
        var pageNow = 1;
        var pageSize = 10;
        var params;
        var searchCondition = "";
        var searchStatus = "";
        var detail = "";


        $(function() {

            $("#searchBtn").click(function () {
                searchType = "showCondition";
                searchCondition = $("#searchCondition").val();
                searchStatus = $("input[name='status']").val();
                params = {userType: userType, searchType: searchType, pageNow: pageNow, pageSize: pageSize, searchCondition: searchCondition, searchStatus: searchStatus};
                ajaxGetData(pageNow);
            });
        });

        function ajaxGetData(pageNow){

            params = {userType: userType, searchType: searchType, pageNow: pageNow, pageSize: pageSize, searchCondition: searchCondition, searchStatus: searchStatus};

            $.ajax({
                type:"POST",
                url:"fullText.action",
                data:params,
                dataType:"text json",
                success:function(data){

                    var elem1 = "";//fresh 和 archive显示的内容不一样
                    var elem2 = "";

                    if(searchStatus == "fresh"){
                        elem1 = "<tr><td>Opt</td><td><span class='glyphicon glyphicon-edit'></span></td><td>Id</td><td>Series</td><td>Title</td><td><span class='glyphicon glyphicon-home'></span></td><td><span class='glyphicon glyphicon-user'></span></td><td><span class='glyphicon glyphicon-calendar'></span></td></tr>";
                        detail = "Detail";
                    }else{
                        elem1 = "<tr><td><span class='glyphicon glyphicon-edit'></span></td><td>Id</td><td>Series</td><td>Title</td><td><span class='glyphicon glyphicon-user'></span></td><td><span class='glyphicon glyphicon-calendar'></span></td><td>MarkDown</td></tr>";
                        detail = "Detail2";

                    }




                    var htmlStr = "";
                    htmlStr += "<table class='table table-hover'>";
                    htmlStr += "<thead>";
                    htmlStr += elem1;
                    htmlStr += "</thead>";
                    htmlStr += "<tbody>";
                    $.each(data.list, function(i, item) {

                        i++;

                        var grantId = item.grantId;
                        var grantSeries = item.grantSeries;
                        var grantTitle = item.grantTitle;
                        var schStr = item.schStr;
                        var grantPeopleCount = item.grantPeopleCount;
                        var hurryDeadline = item.hurryDeadline;

                        htmlStr += "<tr>";

                        if(searchStatus == "fresh"){

                            htmlStr += "<td><input type='checkbox' name='deleteId' value='"+item.grantId+ "'/></ td>"+

                                    "<td><a style='color:#60F;' target='_blank' href='showModify.action?grantId="+ item.grantId +"'><span class='glyphicon glyphicon-pencil' style='color:#FF5706'></span></a></td>"+

                                    "<td>" + i + "</td>" +

                                    "<td>" + item.grantSeries + "</td >" + "<td><a href='showDetail.action?grantId="+ item.grantId+"' target='_blank'>"+item.grantTitle+"</a></td>"+

                                    "<td>" + item.schStr + "</td>" +

                                    "<td id='"+item.grantId+"' class='getGrantId2' onclick='getDataForSubscribeOfRSO();'>"+item.grantPeopleCount+"</td>"+

                                    "<td>" + item.hurryDeadline + "</td>" +

                                    "<td id='putIdHere2'></td>";


                        }else{

                            htmlStr +=

                                    "<td data-toggle='modal' data-target='.bs-example-modal-sm'><span class='glyphicon glyphicon-pencil getGrantId' style='color:#A007FF' id='"+item.grantId+"'></span></td>"+

                                    "<td>" + i + "</td>" +
                                    "<td>" + grantSeries + "</td>" +

                                    "<td>" +
                                    "<a href='show"+ detail +".action?grantId=" + grantId + "' target='_blank'>" +
                                    grantTitle + "</a>" +
                                    "</td>" +

                                    "<td id='"+item.grantId+"' class='getGrantId2' onclick='getDataForSubscribeOfRSO();'>"+item.grantPeopleCount+"</td>"+

                                    "<td>" + hurryDeadline + "</td>"+
                                    "<td>"+item.grantMarkDownByRSO+"</td>"+
                                    "<td id='putIdHere2'></td>";

                            //创建一个子div

                        }


                    });

                    htmlStr += "</tr>";
                    htmlStr += "</tbody>";
                    htmlStr += "<tfoot>";
                    htmlStr += "<tr>";
                        htmlStr += "<td colspan='6'>";
                    if(searchStatus == "fresh"){
                        htmlStr += "<button onclick='getCheckForAjaxDelete();' class='btn btn-danger btn-sm' style='margin-left: -5px;'><span class='glyphicon glyphicon-trash'></span></button>";
                    }
                        /*
                        htmlStr += "<span>current:"+ pageNow + " 共有记录" + data.totalCount + ";共<span id='pageCount'>" + (data.totalCount % 3 == 0 ? parseInt(data.totalCount / 3) : parseInt(data.totalCount / 3 + 1)) + "</span>页" + "</span>";
                        htmlStr += "<button class='btn-info' onclick='goPrevPage();' >前一页</button>&nbsp;&nbsp; ";
                        htmlStr += "<button class='btn-info' onclick='goNextPage()'>后一页</button>&nbsp;&nbsp; ";
                        */
                        htmlStr += "</td>";
                        htmlStr += "</tr>";
                        htmlStr += "</tfoot>";
                        htmlStr += "</table>";
                        htmlStr += "<div style='margin-left:280px;'><button class='btn-info' onclick='goPrevPage();' style='margin-top:30px;' >Previous</button>&nbsp;&nbsp; ";
                        htmlStr += "<span style='margin-top:40px;'>Current:"+pageNow+" &nbsp;TotalCount: "+ data.totalCount +"&nbsp;TotalPage:<span id='pageCount'>" + (data.totalCount % 10 == 0 ? parseInt(data.totalCount / 10) : parseInt(data.totalCount / 10 + 1)) + "</span>" + "</span>&nbsp;&nbsp;";
                        htmlStr += "<button class='btn-info' onclick='goNextPage()'>Next</button>&nbsp;&nbsp;</div>";

                        $("#show").html(htmlStr);

                    getId();

                    getId2();


                },
                  error:function(data){
                        alert("Error, Please Try Later!");
                    }
                });
        }

        function goPrevPage(){
            if(pageNow >= 2){
                pageNow -= 1;
            }else{
                pageNow = 1;
            }
            ajaxGetData(pageNow);
        }

        function goNextPage(){
            if (pageNow + 1 <= parseInt($("#pageCount").text())) {
                pageNow += 1;
            }
            ajaxGetData(pageNow);
        }

        function getId(){
            var rgid;//不能拆成一个动作，不能刚赋完值又立即取值，应该有先后顺序！
            $(".getGrantId").mouseover(function(e) {
                rgid = $(e.target).attr('id');//鼠标移到这个按钮时获取这个rgid属性
            });
            $(".getGrantId").click(function(e2){
                $("#putIdHere").val(rgid);//鼠标点击的时候给这个表单中的input赋值
            });
        }

    </script>

    <script>
        function getCheckForAjaxDelete(){
            var info = "deleteRG";
            var deleteIds = document.getElementsByName("deleteId");
            var idsOfStr = "";
            var count = 0;
            for(var i = 0; i < deleteIds.length; i++){
                if(deleteIds[i].checked){
                    idsOfStr += deleteIds[i].value + "-";
                    count++;
                }
            }
            if(count == 0){
                alert('Please Choose One Grant At Least!');
            }else{
                var params = {info: info, idsOfStr: idsOfStr};
                $.ajax({
                    type:"POST",
                    url:"ajaxDelete.action",
                    data:params,
                    dataType:"text json",
                    success:function(data){
                        alert("Delete Successfully!");
                        ajaxGetData(pageNow);
                    },
                    error:function(data){
                        alert("Delete Error!");
                    }
                });

            }
        }
    </script>

    <script>

        function getId2() {

            var rgid;//不能拆成一个动作，不能刚赋完值又立即取值，应该有先后顺序！
            $(".getGrantId2").mouseover(function(e) {
                rgid = $(e.target).attr('id');//鼠标移到这个按钮时获取这个rgid属性
                $("#putIdHere2").val(rgid);//鼠标点击的时候给这个表单中的input赋值
            });
        }

        function getDataForSubscribeOfRSO(){

            var argu = {grantId: $("#putIdHere2").val()};
            $.ajax({
                type:"POST",
                url:"subscribe.action",
                data:argu,
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

        }
    </script>

    <script>
       function getDataForMarkDown(){
           var rgidForMarkDown = $("#putIdHere").val();
           var contentForMarkDown = $("#markDown").val();
           var info2 = "markRG";
           var params2 = {rgidForMarkDown: rgidForMarkDown, contentForMarkDown: contentForMarkDown, info: info2};
           $.ajax({
               type:"POST",
               url:"ajaxMark.action",
               data:params2,
               dataType:"text json",
               success:function(data){
                   alert("Mark Successfully!");
                   ajaxGetData(pageNow);
               },
               error:function(data){
                   alert("Mark Error!");
               }
           });

       }

   </script>

    <script>




        $(document).ready(function(){
            var freshChartData = [];
            var outdateChartData = [];
            var xForChart = ['SCE', 'NBS', 'EEE', 'CEE', 'SBC', 'WKW', 'MAE'];
            var infoForChart = "infoForChart";
            var paramsForChart = {info:infoForChart};

            $.ajax({
                type:"POST",
                url:"ajaxChart.action",
                data:paramsForChart,
                dataType:"text json",
                success:function(data){

                    $.each(data.chartData, function(i, item) {
                        if(item.type == "fresh"){
                            //alert(typeof(item.number));
                            freshChartData.push(parseInt(item.number));
                        }else{
                            outdateChartData.push(parseInt(item.number)*(-1));
                        }
                    });

                    $('#barchart').highcharts(//total data
                            {
                                chart: {
                                    type: 'column'
                                },
                                title: {
                                    text: 'The number of each school subscribe and achived data '
                                },
                                xAxis: {
                                    categories: xForChart
                                },
                                yAxis: {
                                    allowDecimals: false,
                                    title: {
                                        text: 'Number'
                                    }
                                },
                                credits: {
                                    enabled: false
                                },
                                series: [{
                                    name: 'subscribed',
                                    data: freshChartData
                                }, {
                                    name: 'achived',
                                    data: outdateChartData
                                }]
                            });


                },
                error:function(data){
                    alert("Get Chart Error!");
                }
            });





        });
    </script>

    <script>
        function getChartData(){

            var infoForChart = "infoForChart";
            var paramsForChart = {info:infoForChart};
            $.ajax({
                type:"POST",
                url:"ajaxChart.action",
                data:paramsForChart,
                dataType:"text json",
                success:function(data){

                    $.each(data.chartData, function(i, item) {
                        if(item.type == "fresh"){
                            //alert(typeof(item.number));
                            freshChartData.push(parseInt(item.number));
                        }else{
                            outdateChartData.push(parseInt(item.number));
                        }
                    });
                    alert(freshChartData+"hehe");
                },
                error:function(data){
                    alert("Get Chart Error!");
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
                <li><a href="jumpToRSO.jsp" target="_self">Category</a></li>
                <li><a href="jumpToRSO2.jsp" target="_self">Archived</a></li>
                <li class="active"><a href="searchRSO1.jsp" target="_self">Search</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <form>
            <div class="row" style="position:absolute; left:42%; top:10%;"><!--Search-->
                <div class="row">
                    <div class="col-lg-6">
                        <div class="input-group">
                            <button id="searchBtn" type="button" class="btn btn-info" style="position:absolute; left:410px; top:3px;"><span class="glyphicon glyphicon-search"></span></button>
                                <input id="searchCondition" type="search" class="form-control" style="width:400px" placeholder="search...">
                            <div style="position:absolute; top:50px">
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="fresh" checked="checked" />Fresh
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="outdate" />Achive
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    </br></br></br></br>

    <div id="barchart" style="position:absolute; left:20%; top:25%; min-width: 960px; height: 300px; margin: 0 auto"></div>

    <div class="col-xs-12 col-sm-10 col-sm-offset-2 col-md-10 col-md-offset-2 main" style="margin-top: 350px;">
        <div id="show">
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
                                <label style="margin-left:10px;">Set MarkDown</label>
                                <input type="text" class="form-control input-sm" style="margin-bottom:15px;" id="markDown">
                                <input id="putIdHere" type="text" style="height:20px; display: none;" name="rgidOfStr" /><!--隐藏value为grantId的input框-->
                                <button class="btn btn-warning btn-md"  onclick="getDataForMarkDown();">Add MarkDown By RSO</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>




</div>
<script src="bootstrap/js/myJavaScript.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>

</body>
</html>
