<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 19/9/14
  Time: 9:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>VerifyRegister</title>
    <script>
        window.onload = function(){
            if(confirm("The username is repeat, please choose another one!")){
                location.href='register.jsp';
            }
        };
    </script>
</head>
<body>

</body>
</html>
