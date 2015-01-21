<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 19/9/14
  Time: 9:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script>
        window.onload = function(){
            if(confirm("Your UserName Is Not Available, Please Register First!")){
                location.href="index.jsp";
            }
        };
    </script>
</head>
<body>
<p>FAIL!</p>
</body>
</html>
