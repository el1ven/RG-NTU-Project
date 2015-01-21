<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 30/12/14
  Time: 5:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Search</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="bootstrap/css/dashboard.css" rel="stylesheet">
    <link href="bootstrap/css/mycss.css" rel="stylesheet">
    <script src="bootstrap/js/jquery-2.1.1.min.js"></script>
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
    <script>

        var userType = '<%=session.getAttribute("userType")%>';
        var searchType = "";
        var pageNow = 1;
        var pageSize = 10;
        var params;
        var searchCondition = "";


        $(function() {

            $("#searchBtn").click(function () {
                searchType = "showCondition";
                searchCondition = $("#searchCondition").val();
                params = {userType: userType, searchType: searchType, pageNow: pageNow, pageSize: pageSize, searchCondition: searchCondition};
                ajaxGetData(pageNow);
            });
        });

        function ajaxGetData(pageNow){

            params = {userType: userType, searchType: searchType, pageNow: pageNow, pageSize: pageSize, searchCondition: searchCondition};

            $.ajax({
                type:"POST",
                url:"fullText.action",
                data:params,
                dataType:"text json",
                success:function(data){

                    var htmlStr = "<br><br><br><br>";
                    htmlStr += "<table class='table table-hover'>";
                    htmlStr += "<thead>";
                    htmlStr += "<tr><td>Opt</td><td>State</td><td>Id</td><td>Series</td><td>Title</td><td>RSO Deadline</td><td>SRO Deadline</td></tr>";
                    htmlStr += "</thead>";
                    htmlStr += "<tbody>";
                    $.each(data.list, function(i, item) {

                        i++;

                        var grantId = item.grantId;
                        var grantSeries = item.grantSeries;
                        var grantTitle = item.grantTitle;
                        var hurryDeadlineByRSO = item.hurryDeadlineByRSO;
                        var hurryDeadlineBySRO = item.hurryDeadlineBySRO;
                        var grantChecked = item.grantChecked;

                        var elem = "";//确定按钮是否失效的字段
                        var elem2 = "";//红绿方块
                        if(grantChecked == "disabled"){
                            elem =  "<input type='checkbox' name='addId' value='"+ item.grantId +"' disabled='disabled'/>";
                            elem2 = "<td><div style='width: 12px; height: 12px; background-color: red; margin-top: 4px;'></div></td>";
                        }else{
                            elem =  "<input type='checkbox' name='addId' value='"+ item.grantId +"'/> ";
                            elem2 = "<td><div style='width: 12px; height: 12px; background-color: green; margin-top: 4px;'></div></td>";
                        }


                        htmlStr += "<tr>";

                        htmlStr += "<td>" +

                                elem

                                 +

                                elem2
                                +

                                "<td>" + i + "</td>" +
                                "<td>" + grantSeries + "</td>" +

                                "<td>" +
                                "<a href='showDetail.action?grantId=" + grantId + "' target='_blank'>" +
                                grantTitle + "</a>" +
                                "</td>" +

                                "<td>" + hurryDeadlineByRSO + "</td>" +
                                "<td>" + hurryDeadlineBySRO + "</td>";
                        //创建一个子div

                        htmlStr += "</tr>";
                    });

                    htmlStr += "</tbody>";
                    htmlStr += "<tfoot>";
                    /*htmlStr += "<tr>";
                    htmlStr += "<td colspan='6'><a style='margin-left:-5px;'><button class='btn btn-danger btn-md' onclick='getCheckForAdd();'><span class='glyphicon glyphicon-heart'></span></button></a>";
                    htmlStr += "<span style='margin-top:40px;'>Current:"+pageNow+" &nbsp;&nbsp; TotalCount "+ data.totalCount +"; TotalPage<span id='pageCount'>" + (data.totalCount % 3 == 0 ? parseInt(data.totalCount / 3) : parseInt(data.totalCount / 3 + 1)) + "</span>" + "</span>";
                    htmlStr += "<button class='btn-info' onclick='goPrevPage();' style='margin-top:30px;' >Previous</button>&nbsp;&nbsp; ";
                    htmlStr += "<button class='btn-info' onclick='goNextPage()'>Next</button>&nbsp;&nbsp;";
                    htmlStr += "</td>";
                    htmlStr += "</tr>";*/
                    htmlStr += "</tfoot>";
                    htmlStr += "</table>";

                    htmlStr += "<button class='btn btn-danger btn-md' onclick='getCheckForAdd();'><span class='glyphicon glyphicon-heart'></span></button><br/>";
                    htmlStr += "<div style='margin-left:380px;'><button class='btn-info' onclick='goPrevPage();' style='margin-top:30px;' >Previous</button>&nbsp;&nbsp; ";
                    htmlStr += "<span style='margin-top:40px;'>Current:"+pageNow+" &nbsp;TotalCount: "+ data.totalCount +"&nbsp;TotalPage:<span id='pageCount'>" + (data.totalCount % 10 == 0 ? parseInt(data.totalCount / 10) : parseInt(data.totalCount / 10 + 1)) + "</span>" + "</span>&nbsp;&nbsp;";
                    htmlStr += "<button class='btn-info' onclick='goNextPage()'>Next</button>&nbsp;&nbsp;</div>";


                    $("#show").html(htmlStr);

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
                <li><a href="jumpToFM1.jsp" target="_self">Category</a></li>
                <li><a href="jumpToFM2.jsp" target="_self">My List</a></li>
                <li class="active"><a href="search.jsp" target="_self">Search</a></li>
            </ul>
        </div>
    </div>

    <div class="row" style="position:absolute; left:38%; top:10%;"><!--Search-->
        <div class="row">
            <div class="col-lg-6">
                <div class="input-group">
                    <button id="searchBtn" class="btn btn-info" style="position:absolute; left:410px; top:3px;"><span class="glyphicon glyphicon-search"></span></button>
                    <input id="searchCondition" type="search" class="form-control" style="width:400px" placeholder="search..."/>
                </div>
            </div>
        </div>
    </div>

    </br></br>

    <div class="col-xs-12 col-sm-10 col-sm-offset-2 col-md-10 col-md-offset-1 main">
        <div id="show">
        </div>
    </div>

</div>

</div>
<script src="bootstrap/js/myJavaScript.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>
