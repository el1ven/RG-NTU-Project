<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 19/9/14
  Time: 9:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>verifyLogin</title>
    <script>
        window.onload = function(){
            if(confirm("Username is not available, please register first!")){
                location.href='index.jsp';
            }
        };
    </script>
</head>
<body>
Fail!
</body>
</html>
