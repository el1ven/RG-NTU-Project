<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 24/10/14
  Time: 9:52 AM
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
    <meta charset="utf-8">
    <title>Form</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="bootstrap/css/jasny-bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form action="publish.action" method="post" enctype="multipart/form-data">
    <div class="container">
        <div class="row" style="margin-top:50px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:70px;">Title</label>
                <div class="col-sm-6 col-sm-offset-2">
                    <input id="title" name="grantTitle" type="text" class="form-control input-sm" style="margin-left:-180px;">
                </div>
            </div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:70px;">Series</label>
                <div class="col-sm-6 col-sm-offset-2">
                    <input id="series" name="grantSeries" type="text" class="form-control input-sm" style="margin-left:-180px;">
                </div>
            </div>
        </div>
        <div class="row" style="margin-top:10px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-5 control-label" style="font-size:20px; margin-left:70px;">Person of Contact</label>
                <div class="col-sm-6">
                    <input id="person" name="grantContact" type="text" class="form-control input-sm"  style="margin-left:-180px;">
                </div>
            </div>
        </div>
        <hr>

        <div class="row" style="margin-top:10px;">
            <div class="col-md-7 col-md-offset-3">
                <label class="col-sm-2 control-label" style="font-size:20px; margin-left:-30px;">Content</label>
            </div>
        </div>

        <div class="row" style="margin-top:20px;">
            <!--<textarea name="grantContent" class="form-control" rows="5" style="margin-left:-10px;"></textarea>-->
            <div class="row" style="margin-top:10px;"><!-- Textarea模块 -->
                <div class="col-md-7 col-md-offset-3">
                    <textarea id="elm1" name="grantContent" style="margin-left:-100px"></textarea>
                </div>
            </div>
        </div>

        <div id="upload_file" class="row" style="margin-top:20px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-4 control-label" style="font-size:20px; margin-left:70px;">Upload Files</label>
            </div>
        </div>

        <div id="upload"  class="row" style="margin-top:10px;">
            <div class="col-md-8 col-md-offset-2">
                <div class="fileinput fileinput-new input-group" data-provides="fileinput"  style="margin-left:90px;">
                    <div class="form-control">
                        <i class="glyphicon glyphicon-file fileinput-exists"></i>
                        <span class="fileinput-filename"></span>
                    </div>
                        <span class="input-group-addon btn btn-success btn-file">
                            <span class="fileinput-new">Select file</span>
                            <span class="fileinput-exists">Change</span>
                            <input id="files" type="file" name="upload"/><!--grantFile-->
                        </span>
                    <a href="#" class="input-group-addon btn btn-danger fileinput-exists" data-dismiss="fileinput">Remove</a>
                </div>
            </div>
        </div>

        <div id="loadicon" class="row">
            <div class="col-md-1 col-md-offset-3">
                <button id="increase" type="button" class="btn btn-warning" style="margin-left:-10px;">
                    <span class="glyphicon glyphicon-plus-sign"></span>
                </button>
            </div>
            <div class="col-md-1">
                <button id="remove" type="button" class="btn btn-warning" style="display:none;">
                    <span class="glyphicon glyphicon-remove-circle"></span>
                </button>
            </div>
        </div>

        <!--<div class="row" style="margin-top:30px;">
            <div class="col-md-9 col-md-offset-3">
                <div class="form-group">
                    <label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">Deadline:</label>
                    <div class="col-sm-3">
                        <input type="date" name="deadlineDates" class="form-control" style="margin-left:-125px; height:24px;">
                    </div>
                    <!--<label class="col-sm-2 control-label" style="font-size:20px; margin-left:-120px;">To</label>
                    <div class="col-sm-3">
                        <input type="date" class="form-control input-sm" style="margin-left:-80px; height:24px;">
                    </div>
                </div>
            </div>
        </div>

        <div id="description" class="row" style="margin-top:15px;">
            <div class="col-md-8 col-md-offset-3">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">Description</label>
                <textarea name="deadlineContents" class="form-control" rows="5" style="margin-left:-10px;"></textarea>
            </div>
        </div>-->

        <div id="deadline_setting" class="row" style="margin-top:30px;">
            <div class="col-md-9 col-md-offset-3">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">Deadline Setting</label><br>
            </div>
        </div>

        <div id="date_time" class="row" style="margin-top:10px;">
            <div class="col-md-8 col-md-offset-2">
                <label class="control-label" style="font-size:20px; margin-left:-60px;">Date</label>
                <div class="col-sm-4">
                    <input type="date" name="deadlineDates" class="form-control input-sm" style="margin-left:250px; height:24px;">
                </div>
            </div>
        </div>

        <div id="description" class="row" style="margin-top:15px; margin-left:130px;">
            <div class="col-md-8 col-md-offset-3">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">Description</label>
                <textarea class="form-control" name="deadlineContents" rows="2" style="margin-left:45px; width:610px;"></textarea>
            </div>
        </div>

        <div id="icon" class="row" style="margin-top:15px;">
            <div class="col-md-1 col-md-offset-3">
                <button id="add" type="button" class="btn btn-info" style="margin-left:-10px;">
                    <span class="glyphicon glyphicon-plus-sign"></span>
                </button>
            </div>
            <div class="col-md-1">
                <button id="minus" type="button" class="btn btn-info" style="display:none;">
                    <span class="glyphicon glyphicon-minus-sign"></span>
                </button>
            </div>
        </div>

        <div class="row" style="margin-top:80px; margin-bottom:50px;">
            <div class="col-md-3 col-md-offset-5">
                <input value="submit" type="submit" class="btn btn-danger btn-lg" style="margin-left:80px;"/>
            </div>
        </div>
    </div>
</form>
<script src="bootstrap/js/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<script src="bootstrap/js/jasny-bootstrap.min.js"></script>
<script src="bootstrap/js/myJavaScript.js"></script>
<script type="text/javascript" src="bootstrap/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">//textarea模块
    tinymce.init({
        selector: "textarea#elm1",
        theme: "modern",
        width: 700,
        height: 300,
        plugins:
                [
                    "advlist autolink link image lists charmap print preview hr anchor pagebreak spellchecker",
                    "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                    "save table contextmenu directionality emoticons template paste textcolor"
                ],
        content_css: "css/content.css",
        toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | l      ink image | print preview media fullpage | forecolor backcolor emoticons",
        style_formats:
                [
                    {title: 'Bold text', inline: 'b'},
                    {title: 'Red text', inline: 'span', styles: {color: '#ff0000'}},
                    {title: 'Red header', block: 'h1', styles: {color: '#ff0000'}},
                    {title: 'Example 1', inline: 'span', classes: 'example1'},
                    {title: 'Example 2', inline: 'span', classes: 'example2'},
                    {title: 'Table styles'},
                    {title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
                ]
    });
</script>
</body>
</html>