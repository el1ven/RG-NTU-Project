<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 19/9/14
  Time: 9:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("isLogin")!="true"){
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title></title>
</head>
<body>
<p>SUCCESS!成功进入主界面</p>
<h2>你好<%=session.getAttribute("userName")%></h2>

</body>
</html>
