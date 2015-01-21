<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% int i = 0;%>
<%
    if(session.getAttribute("isLogin")!="true"){
        response.sendRedirect("index.jsp");
    }
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Faculty Home 2</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/dashboard.css" rel="stylesheet">
<link href="bootstrap/css/mycss.css" rel="stylesheet">
<script src="bootstrap/js/jquery.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<script src="bootstrap/js/myJavaScript.js"></script>
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
                var url = "deleteOfFM.action?userName=<%=session.getAttribute("userName")%>&ids="+idArr;
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
                var userName = "<%=session.getAttribute("userName")%>";
                var params = {rgid:rgid, userName: userName, info: "getDeadlineDataByFM"};
                $.ajax({
                    type:"POST",
                    url:"ajaxDeadlineByFM.action",
                    data:params,
                    dataType:"text json",
                    success:function(data){
                        $.each(data.list, function(i, item) {
                            if(item.deadline1 == "None"){
                                $("#deadline1").val("");
                            }else{
                                $("#deadline1").val(item.deadline1);
                            }

                            if(item.deadline2 == "None"){
                                $("#deadline2").val("");
                            }else{
                                $("#deadline2").val(item.deadline2);
                            }

                            if(item.deadline3 == "None"){
                                $("#deadline3").val("");
                            }else{
                                $("#deadline3").val(item.deadline3);
                            }
                        });
                    },
                    error:function(data){
                        alert("Info Error!");
                    }
                });
            });
            $(".getGrantId").click(function(e2){
                $("#putIdHere").val(rgid);//鼠标点击的时候给这个表单中的input赋值
            });
        });

        function setDeadlineByFM(){
            var rgid = $("#putIdHere").val();
            var userName = "<%=session.getAttribute("userName")%>";
            var deadline1 = $("#deadline1").val();
            var deadline2 = $("#deadline2").val();
            var deadline3 = $("#deadline3").val();


            var argus = {rgid: rgid, userName: userName, deadline1: deadline1, deadline2: deadline2, deadline3: deadline3, info:"setDeadlineDataByFM"};
            $.ajax({
                type:"POST",
                url:"ajaxDeadlineByFM.action",
                data:argus,
                dataType:"text json",
                success:function(data){
                    alert("Set Successfully!");
                },
                error:function(data){
                    alert("Info Error!");
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
          <label style="color:#FFF; font-size:18px; margin-top:10px; margin-left:180px;">Hi,<%=session.getAttribute("userName")%></label>
       </div>
      </div>
    </div>
    
    <div class="container-fluid">
      <div class="row"><!-- 侧边导航 -->
          <div id="slidebar" class="col-sm-2 col-md-1 mysidebar">
            <ul class="nav nav-sidebar" id="nav-sidebar">
                <li><a href="jumpToFM1.jsp" target="_self">Category</a></li>
                <li class="active"><a href="jumpToFM2.jsp" target="_self">My List</a></li>
                <li><a href="search.jsp" target="_self">Search</a></li>
            </ul>
          </div>
      </div>
      <div class="row">
        <div class="col-md-10 col-md-offset-1">
          <h2 class="sub-header">My List</h2>
          <div class="table-responsive">
            <table id="tab1" class="table table-hover">
              <thead>
                <tr>
                  <th>Opt</th>
                  <th>Setting</th>
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
                        <td><input type="checkbox" name="deleteId" value="<%=request.getAttribute("grantId")%>"></td>
                        <td data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-cog getGrantId" id="<%=request.getAttribute("grantId")%>"></span></td>
                        <td><%=++i%></td>
                        <!--注意这里面的属性是bean的属性名称而不是数据库中的属性名称-->
                        <td><%=request.getAttribute("grantSeries")%></td>
                        <td><a href="showDetail.action?grantId=<%=request.getAttribute("grantId")%>" target="_blank"><%=request.getAttribute("grantTitle")%></a></td>
                        <td><%=request.getAttribute("hurryDeadline")%></td>
                        <td><%=request.getAttribute("hurryDeadline2")%></td>
                        <td id="grantHideId" style="visibility: hidden;"><%=request.getAttribute("grantId")%></td>
                    </tr>
                </s:iterator>
              </tbody>
            </table>
            <br>
              <a style="margin-left:-5px;">
                  <button class="btn btn-danger btn-md" onclick="getCheckForDelete();">
                      <span class="glyphicon glyphicon-trash"></span>
                  </button>
              </a>
            <br>
            <br>
          </div>
        </div>
      </div>
   
   </div>
   
   <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-dialog modal-sm">
    		<div class="modal-content">
            	<div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                	<div class="container" style="margin-left:40px;">
        			  <div class="row">
            		    <div class="col-sm-2">

                            <input type="text" id="putIdHere" style="display: none;"/>

                            <span class="glyphicon glyphicon-time"></span>
                            <label style="margin-left:10px;">Set Deadline 1</label>
                            <input type="date" class="form-control input-sm" style="margin-bottom:15px;" id="deadline1"/>

                            <span class="glyphicon glyphicon-time"></span>
                            <label style="margin-left:10px;">Set Deadline 2</label>
                            <input type="date" class="form-control input-sm" style="margin-bottom:15px;" id="deadline2"/>

                            <span class="glyphicon glyphicon-time"></span>
                            <label style="margin-left:10px;">Set Deadline 3</label>
                            <input type="date" class="form-control input-sm" style="margin-bottom:15px;" id="deadline3"/>

                            <button class="btn btn-success btn-md" style="margin-left: 40px;" onclick="setDeadlineByFM();">Submit</button>

                        </div>
                      </div>
            		</div>
                </div>
            </div>
        </div>
   </div>
</body>
</html>
