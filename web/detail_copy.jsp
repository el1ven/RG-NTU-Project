<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 24/10/14
  Time: 9:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
        <div class="col-sm-8 col-md-12 blog-main">
            <div class="blog-post">
                <!--<h2 class="blog-post-title">Title</h2>
                <p class="lead blog-description">Series</p>
                <hr/>
                <p class="lead blog-description">Deadline</p>
                <p class="lead blog-description">Deadline Description</p>
                <hr/>
                <p class="lead blog-description">Contact Person</p>
                <hr/>
                <p><a href="#">Upload file</a></p>
                <hr>
                <p>Content</p>-->
                <s:iterator value="grantList">

                    <h2 class="blog-post-title"><%=request.getAttribute("grantTitle")%></h2>
                    <p class="lead blog-description"><%=request.getAttribute("grantSeries")%></p>
                    <hr/>

                    <%
                        String str1 = (String) request.getAttribute("grantDeadlineOfDetail");
                        String str2 = (String) request.getAttribute("grantDeadlineDescriptionOfDetail");
                        String [] arr1 = str1.split("    ");
                        String [] arr2 = str2.split("    ");
                        int count = arr1.length;
                        for(int i = 0; i < count; i++){
                            out.println(i+1);
                            out.println(arr1[i]);
                            out.println(arr2[i]);
                            out.println("<br>");
                        }
                    %>

                    <hr/>
                    <p class="lead blog-description"><%=request.getAttribute("grantContact")%></p>
                    <hr/>

                    <%
                        String str3 = (String) request.getAttribute("grantFileNameOfDetail");
                        String str4 = (String) request.getAttribute("grantFileName2OfDetail");
                        String [] arr3 = str3.split("    ");
                        String [] arr4 = str4.split("    ");
                        int count2 = arr3.length;
                        for(int i = 0; i < count2; i++){
                            out.print(i+1);
                            out.println("<a href=download.action?fileName="+arr4[i]+"&grantId="+request.getAttribute("grantId")+">"+arr3[i]+"</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;");
                        }
                    %>
                    <hr>
                    <p><%=request.getAttribute("grantContent")%></p>

                </s:iterator>
                <hr/>
            </div>
        </div>
    </div>
</div>
</body>
</html>