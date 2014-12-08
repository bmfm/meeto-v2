<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <title>Assign Action</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">
</head>
<body>

<div class="col-md-9 column">

    <s:form role="form" class="col-md-12" action="assignTask">
        <s:hidden name="req" value="addItem"/>
        <s:hidden name="meetingidhiddenforaction" id="meetingidhiddenforaction" value="%{meetingidfromroomform}"/>

        <s:radio list="list" name="userToAssignAction"/>

        <div class="form-group">
            <label for="actionname">Task to assign:</label>
            <input class="form-control" name="actionname" id="actionname" type="text"/>

        </div>

        <button type="submit" id="submitAssignActionBtn" class="btn btn-primary">Assign Action</button>

    </s:form>
    <p>&nbsp;</p>

    <div class="panel panel-default">
        <div class="panel-body" id="messagesarea">

            <s:if test="hasActionMessages()">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <strong>Success!</strong> <s:actionmessage/>
                </div>
            </s:if>

            <s:elseif test="hasActionErrors()">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <strong>Error!</strong> <s:actionerror/>
                </div>
            </s:elseif>

        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-meetingroom.js"></script>

</body>
</html>
