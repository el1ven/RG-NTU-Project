<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 29/12/14
  Time: 2:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Email Form</title>
</head>
<body>
<em>The form below uses Google's SMTP server.
    So you need to enter a gmail username and password
</em>
<form action="email.action" method="post">
    <label id="from">From</label><br/>
    <input type="text" name="from"/><br/>
    <label id="password">Password</label><br/>
    <input type="password" name="password"/><br/>
    <label id="to">To</label><br/>
    <input type="text" name="to"/><br/>
    <label id="subject">Subject</label><br/>
    <input type="text" name="subject"/><br/>
    <label id="body">Body</label><br/>
    <input type="text" name="body"/><br/>
    <input type="submit" value="Send Email"/>
</form>
</body>
</html>
