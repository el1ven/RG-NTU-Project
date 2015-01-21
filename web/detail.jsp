<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Detail</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">

    <link href="bootstrap/css/blog.css" rel="stylesheet">
    <link href="bootstrap/css/mycss.css" rel="stylesheet">
</head>
<body>
<div class="container" style="margin-top:50px;">
    <div class="row">
        <s:iterator value="grantList">
            <div class="col-sm-8 col-md-12 blog-main">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <%=request.getAttribute("grantTitle")%>
                    </div><!--Title -->
                    <div class="panel-body">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>Series</th><!--Series -->
                                <th>Contact Person</th><!--Person -->
                                <th>Attachment</th><!--Attachment -->
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><%=request.getAttribute("grantSeries")%></td>
                                <td><%=request.getAttribute("grantContact")%></td>
                                <td>
                                    <%
                                        String str3 = (String) request.getAttribute("grantFileNameOfDetail");
                                        String str4 = (String) request.getAttribute("grantFileName2OfDetail");
                                        String [] arr3 = str3.split("    ");
                                        String [] arr4 = str4.split("    ");
                                        int count2 = arr3.length;
                                        for(int i = 0; i < count2; i++)
                                        {
                                            if(arr3[i]!=""){
                                                out.print(i+1);
                                                out.println("<a href=download.action?fileName="+arr4[i]+"&grantId="+request.getAttribute("grantId")+">"+arr3[i]+"</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;");
                                            }

                                        }
                                    %>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <%=request.getAttribute("grantContent")%><!--Content -->
                    </div>
                    <div class="panel-footer"> <!--DeadLine -->
                        <%
                            String str1 = (String) request.getAttribute("grantDeadlineOfDetail");
                            String str2 = (String) request.getAttribute("grantDeadlineDescriptionOfDetail");
                            String [] arr1 = str1.split("    ");
                            String [] arr2 = str2.split("    ");
                            int count = arr1.length;
                            for(int i = 0; i < count; i++){
                                if(arr1[i]!=""){
                                    out.println(i+1);
                                    out.println(arr1[i]+"&nbsp;&nbsp;");
                                    out.println(arr2[i]);
                                    out.println("<br>");
                                }

                            }
                        %>
                    </div>
                </div>
            </div>
        </s:iterator>
    </div>
</div>
</body>
</html>