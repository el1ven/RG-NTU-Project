<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 24/12/14
  Time: 10:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--<form name="loginForm" action="login.action" method="post">
UserName:<input type="text" name="userName" required/><br><br>
Password:<input type="password" name="userPassword" required/><br><br>
<input type="submit" value="Submit"/>
<input type="reset" value="Reset"/>
</form>-->
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>MainPage</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="bootstrap/css/mycss.css" rel="stylesheet">
    <script src="bootstrap/js/jquery-2.1.1.min.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        //为了在前台显示注册成功或是失败的消息
        var msg="${requestScope.MessageOfRegister}";
        if(msg == "Register Successfully!"){alert(msg);}
        if(msg == "Register Fail!"){alert(msg);}
    </script>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div class="navbar-collapse collapse">

            <!--登录部分----------------------------->
            <button class="btn btn-success navbar-btn navbar-left" data-toggle="modal" data-target=".bs-example-modal-lg">Create New Account</button>
            <form class="navbar-form navbar-right" role="form" name="loginForm" action="login.action" method="post">
                <div class="form-group">
                    <input type="text" name="userName" placeholder="Username"  class="form-control" required/>
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Password" name="userPassword" class="form-control" required/>
                </div>
                <input type="submit" value="Login" class="btn btn-success"/>
            </form>

        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                <h1 class="form-signin-heading" style="margin-left:20px;">Welcome to Create New Account</h1>
            </div>
            <div class="modal-body">
                <div class="container" style="margin-top:20px">
                    <div class="row">
                        <div class="col-lg-5 col-lg-offset-2">
                            <!--注册部分----------------------------->
                            <form class="form-signin" role="form" name="registerForm" action="register.action" method="post">
                                <input type="text" class="form-control" name="userName" id="facultyName" placeholder="Name" style="margin-bottom:20px;">
                                <!--<div id="checkbox">
                                    <P style="margin-bottom:-5px;">
                                        <span><input type="checkbox" name="school" value="MAE">MAE</span>
                                        <span><input type="checkbox" name="school" value="SCE">SCE</span>
                                        <span><input type="checkbox" name="school" value="WKW">WKW</span>
                                        <span><input type="checkbox" name="school" value="NBS">NBS</span>
                                        <span><input type="checkbox" name="school" value="EEE">EEE</span>
                                        <span><input type="checkbox" name="school" value="MAE">MAE</span>
                                        <span><input type="checkbox" name="school" value="SCE">SCE</span>
                                        <span><input type="checkbox" name="school" value="WKW">WKW</span>
                                    <hr>
                                    </P>
                                    <P style="margin-top:-5px;">
                                        <span><input type="checkbox" name="school" value="MAE">NBS</span>
                                        <span><input type="checkbox" name="school" value="SCE">WKW</span>
                                        <span><input type="checkbox" name="school" value="WKW">MAE</span>
                                        <span><input type="checkbox" name="school" value="NBS">EEE</span>
                                        <span><input type="checkbox" name="school" value="EEE">SCE</span>
                                        <span><input type="checkbox" name="school" value="MAE">NBS</span>
                                        <span><input type="checkbox" name="school" value="SCE">WKW</span>
                                        <span><input type="checkbox" name="school" value="WKW">EEE</span>
                                    </P>
                                </div>-->
                                <input name="userPassword" type="password" class="form-control" placeholder="Password" style="margin-bottom:20px;" required autofocus>
                                <input name="userEmail" type="email" class="form-control" placeholder="NTU_Email" style="margin-bottom:20px;" required autofocus>
                                <select name="userType" class="form-control" style="margin-bottom:20px;" required autofocus>
                                    <option value="FM">FacultyMember</option>
                                    <option value="RSO">RSO</option>
                                    <option value="SRO">SRO</option>
                                </select>

                                <select name="userSchool1" class="form-control" style="margin-bottom:20px;">
                                    <option value="None">Please Choose</option>
                                    <s:iterator value="schList">
                                    <option value="<%=request.getAttribute("schName")%>"><%=request.getAttribute("schName")%></option>
                                    </s:iterator>
                                </select>
                                <select name="userSchool2" class="form-control" style="margin-bottom:20px;">
                                    <option value="None">Please Choose</option>
                                    <s:iterator value="schList">
                                        <option value="<%=request.getAttribute("schName")%>"><%=request.getAttribute("schName")%></option>
                                    </s:iterator>

                                </select>
                                <select name="userSchool3" class="form-control">
                                    <option value="None">Please Choose</option>
                                    <s:iterator value="schList">
                                        <option value="<%=request.getAttribute("schName")%>"><%=request.getAttribute("schName")%></option>
                                    </s:iterator>
                                </select>

                                <!--<input type="password" class="form-control" placeholder="Confirm Password" style="margin-bottom:20px;" required autofocus>-->
                                <!--<div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="userRemember" value="remember-me">Remember me
                                    </label>
                                </div>-->
                                <div style="margin-top:50px;">
                                    <input type="submit" value="Sign in" class="btn btn-lg btn-success" style="margin-left:180px;"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="jumbotron">
    <div class="container">
        <h1>Hello, world!</h1>
        <p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
        <p><a class="btn btn-primary btn-lg" role="button">Learn more &raquo;</a></p>
    </div>
</div>

<div class="container">
    <!-- Example row of columns -->
    <div class="row">
        <div class="col-md-4">
            <h2>Heading One</h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>Heading Two</h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>Heading Three</h2>
            <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
    </div>

    <hr>

    <footer>
        <p>&copy; Company 2014</p>
    </footer>
</div>
</body>
</html>

