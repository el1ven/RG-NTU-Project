<%--
  Created by IntelliJ IDEA.
  User: el1ven
  Date: 27/12/14
  Time: 4:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
    <script src="bootstrap/js/jquery-2.1.1.min.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
    <script src="bootstrap/js/bootstrap-wysiwyg.js"></script>
    <script src="bootstrap/js/jasny-bootstrap.min.js"></script>
    <script src="bootstrap/external/jquery.hotkeys.js"></script>
    <script src="bootstrap/external/google-code-prettify/prettify.js"></script>
    <script src="bootstrap/js/myJavaScript.js"></script>
    <style>
        #editor {
            max-height: 250px;
            height: 150px;
            width: 780px;
            background-color: white;
            border-collapse: separate;
            border: 1px solid rgb(204, 204, 204);
            padding: 4px;
            box-sizing: content-box;
            -webkit-box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px 0px inset;
            box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px 0px inset;
            border-top-right-radius: 3px; border-bottom-right-radius: 3px;
            border-bottom-left-radius: 3px; border-top-left-radius: 3px;
            overflow: scroll;
            outline: none;
        }
        #voiceBtn {
            width: 20px;
            color: transparent;
            background-color: transparent;
            transform: scale(2.0, 2.0);
            -webkit-transform: scale(2.0, 2.0);
            -moz-transform: scale(2.0, 2.0);
            border: transparent;
            cursor: pointer;
            box-shadow: none;
            -webkit-box-shadow: none;
        }
        div[data-role="editor-toolbar"] {
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }
        .dropdown-menu a {
            cursor: pointer;
        }

    </style>
    <script>
        $(function()
        {
            $('#add').click(function()
            {
                /*$('#description').after('' +
                 '<div class="row" style="margin-top:30px;">' +
                 '<div class="col-md-9 col-md-offset-3">' +
                 '<div class="form-group">' +
                 '<label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">Deadline:</label>' +
                 '<div class="col-sm-3">' +
                 '<input type="date" name="deadlineDates" class="form-control" style="margin-left:-125px; height:24px;">' +
                 '</div></div></div></div><div id="description" class="row" style="margin-top:15px;"><div class="col-md-8 col-md-offset-3"><label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">Description</label><textarea name="deadlineContents" class="form-control" rows="5" style="margin-left:-10px;"></textarea></div></div>');
                 */
                $('#description').after('' +
                        '<div class="row" style="margin-top:30px;">' +
                        '<div class="col-md-9 col-md-offset-3">' +
                        '<label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">DeadLine</label>' +
                        '<br>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row" style="margin-top:10px;">' +
                        '<div class="col-md-8 col-md-offset-2">' +
                        '<label class="control-label" style="font-size:20px; margin-left:-50px;">Date</label>' +
                        '<div class="col-sm-4">' +
                        '<input type="date" class="form-control input-sm" name="deadlineDates" style="margin-left:250px; height:24px;">' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row" style="margin-top:15px; margin-left:130px;">' +
                        '<div class="col-md-8 col-md-offset-3">' +
                        '<label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">Description</label>' +
                        '<textarea name="deadlineContents" class="form-control" rows="2" style="margin-left:45px; width:610px;"></textarea>' +
                        '</div></div>');

                $('#minus').css('display','block');

            });

            $('#minus').click(function()
            {
                $('#icon').prev().remove();
                if($('#description').next().attr("id") == 'icon')
                {
                    $('#minus').css('display','none');
                }
                else
                {
                    $('#minus').css('display','block');
                }
            });

            $('#increase').click(function()
            {
                $('#upload').after('<div class="row" style="margin-top:10px;"><div class="col-md-7 col-md-offset-3"><div class="form-group"><div class="fileinput fileinput-new input-group" data-provides="fileinput"  style="margin-left:-10px;"><div class="form-control"><i class="glyphicon glyphicon-file fileinput-exists"></i><span class="fileinput-filename"></span></div><span class="input-group-addon btn btn-success btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="upload"/></span><a href="#" class="input-group-addon btn btn-danger fileinput-exists" data-dismiss="fileinput">Remove</a></div></div></div></div>');
                $('#remove').css('display','block');
            });
            $('#remove').click(function()
            {
                $('#loadicon').prev().remove();
                if($('#upload').next().attr("id") == 'loadicon')
                {
                    $('#remove').css('display','none');
                }
                else
                {
                    $('#remove').css('display','block');
                }
            });

            $(function()
            {
                initToolbarBootstrapBindings();
                $('#editor').wysiwyg({ fileUploadError: showErrorAlert} );
                window.prettyPrint && prettyPrint();
            });
        });
    </script>
</head>
<body>
<form action="publishAfterModify.action" method="post" enctype="multipart/form-data">
    <div class="container">

        <s:iterator value="grantList">

        <input value="<%=request.getAttribute("grantId")%>" id="grantId" name="grantIdOfStr" type="text" style="visibility: hidden;">

        <div class="row" style="margin-top:50px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:70px;">Title</label>
                <div class="col-sm-6 col-sm-offset-2">
                    <input value="<%=request.getAttribute("grantTitle")%>" id="title" name="grantTitle" type="text" class="form-control input-sm" style="margin-left:-180px;">
                </div>
            </div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:70px;">Series</label>
                <div class="col-sm-6 col-sm-offset-2">
                    <input value="<%=request.getAttribute("grantSeries")%>" id="series" name="grantSeries" type="text" class="form-control input-sm" style="margin-left:-180px;">
                </div>
            </div>
        </div>
        <div class="row" style="margin-top:10px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-5 control-label" style="font-size:20px; margin-left:70px;">Person of Contact</label>
                <div class="col-sm-6">
                    <input value="<%=request.getAttribute("grantContact")%>" id="person" name="grantContact" type="text" class="form-control input-sm"  style="margin-left:-180px;">
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
            <div class="col-md-9 col-md-offset-3">
                <textarea name="grantContent" class="form-control" rows="5" style="margin-left:-10px;"><%=request.getAttribute("grantContent")%></textarea>
                <!--<div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor" style="margin-left:-25px;">
                    <div class="btn-group">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font"><i class="icon-font"></i><b class="caret"></b></a>
                        <ul class="dropdown-menu"></ul>
                    </div>

                    <div class="btn-group">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size"><i class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
                            <li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
                            <li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
                        </ul>
                    </div>

                    <div class="btn-group">
                        <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i class="icon-bold"></i></a>
                        <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a>
                        <a class="btn" data-edit="strikethrough" title="Strikethrough"><i class="icon-strikethrough"></i></a>
                        <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i class="icon-underline"></i></a>
                    </div>

                    <div class="btn-group">
                        <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i class="icon-list-ul"></i></a>
                        <a class="btn" data-edit="insertorderedlist" title="Number list"><i class="icon-list-ol"></i></a>
                        <a class="btn" data-edit="outdent" title="Reduce indent (Shift+Tab)"><i class="icon-indent-left"></i></a>
                        <a class="btn" data-edit="indent" title="Indent (Tab)"><i class="icon-indent-right"></i></a>
                    </div>

                    <div class="btn-group">
                        <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i class="icon-align-left"></i></a>
                        <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i class="icon-align-center"></i></a>
                        <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i class="icon-align-right"></i></a>
                        <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i class="icon-align-justify"></i></a>
                    </div>

                    <div class="btn-group">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Hyperlink"><i class="icon-link"></i></a>
                        <div class="dropdown-menu input-append">
                            <input class="span2" placeholder="URL" type="text" data-edit="createLink"/>
                            <button class="btn" type="button">Add</button>
                        </div>
                        <a class="btn" data-edit="unlink" title="Remove Hyperlink"><i class="icon-cut"></i></a>
                    </div>

                    <div class="btn-group">
                        <a class="btn" title="Insert picture (or just drag & drop)" id="pictureBtn"><i class="icon-picture"></i></a>
                        <input type="file" data-role="magic-overlay" data-target="#pictureBtn" data-edit="insertImage" />
                    </div>

                    <div class="btn-group">
                        <a class="btn" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i class="icon-undo"></i></a>
                        <a class="btn" data-edit="redo" title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
                    </div>
                    <input type="text" data-edit="inserttext" id="voiceBtn" x-webkit-speech="">
                </div>
                <div id="editor" style="margin-left:-15px;">
                </div>
                </div>-->
            </div>
        </div>

        <div class="row" style="margin-top:20px;margin-left:70px">
            <div class="col-md-10 col-md-offset-2">
                <p>deadline删除区</p>
                <%
                    String str = (String) request.getAttribute("grantDeadlineIdOfDetail");
                    String str1 = (String) request.getAttribute("grantDeadlineOfDetail");
                    String str2 = (String) request.getAttribute("grantDeadlineDescriptionOfDetail");
                    String [] arr = str.split("    ");
                    String [] arr1 = str1.split("    ");
                    String [] arr2 = str2.split("    ");
                    int count = arr1.length;
                    for(int i = 0; i < count; i++){
                        out.println(i+1);
                        out.print(arr1[i]+"&nbsp;&nbsp;");
                        out.print(arr2[i]);
                        out.print("&nbsp;&nbsp;<a href='deleteDeadline.action?did="+arr[i]+"&grantId="+request.getAttribute("grantId")+"'>Delete</a>");
                        out.println("<br>");
                    }
                %>
            </div>
        </div>

        <div class="row" style="margin-top:20px;margin-left:70px">
            <div class="col-md-10 col-md-offset-2">
                <p>文件删除区</p>
                <%
                    String str3 = (String) request.getAttribute("grantFileNameOfDetail");
                    String str4 = (String) request.getAttribute("grantFileIdOfDetail");
                    String [] arr3 = str3.split("    ");
                    String [] arr4 = str4.split("    ");
                    int count2 = arr3.length;
                    for(int i = 0; i < count2; i++){
                        out.print(i+1);
                        out.print("&nbsp;<a href=download.action?fileName="+arr3[i]+"&grantId="+request.getAttribute("grantId")+">"+arr3[i]+"</a>");
                        out.print("&nbsp;&nbsp;<a href='deleteFile.action?fid="+arr4[i]+"&grantId="+request.getAttribute("grantId")+"'>Delete</a>");
                        out.print("<br>");
                    }
                %>
            </div>
        </div>

        </s:iterator>



        <div class="row" style="margin-top:20px;">
            <div class="col-md-10 col-md-offset-2">
                <label class="col-sm-4 control-label" style="font-size:20px; margin-left:70px;">Upload Files</label>
            </div>
        </div>

        <div id="upload" class="row" style="margin-top:10px;">
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

        <div class="row" style="margin-top:30px;">
            <div class="col-md-9 col-md-offset-3">
                <label class="col-sm-3 control-label" style="font-size:20px; margin-left:-30px;">DeadLine</label><br>
            </div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col-md-8 col-md-offset-2">
                <label class="control-label" style="font-size:20px; margin-left:-50px;">Date</label>
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
                <input value="modify" type="submit" class="btn btn-success btn-lg" style="margin-left:150px;"/>
            </div>
        </div>
    </div>
</form>
</body>
</html>