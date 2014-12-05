<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

    <title>meetings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">


</head>


<body>

<div class="row clearfix">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="col-md-12 column">

                <div class="form-group">
                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th>Participants</th>

                        </tr>
                        </thead>
                        <tbody>

                        <s:iterator value="participantsList" status="status" var="listContent">

                            <tr>


                            <td><s:property value="username"/></td>


                            </tr>


                        </s:iterator>
                        </tbody>
                    </table>


                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th>Agenda Item</th>
                            <th>Description</th>
                            <th>Key Decision</th>

                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="agendaItemsList" status="status" var="listContent">

                            <tr>


                                <td><s:property value="itemname"/></td>
                                <td><s:property value="itemdescription"/></td>
                                <td><s:property value="keydecision"/></td>


                            </tr>


                        </s:iterator>

                        </tbody>
                    </table>

                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th>Action assignee</th>
                            <th>Description</th>
                            <th>Status</th>

                        </tr>
                        </thead>
                        <tbody>

                        <s:iterator value="actionsList" status="status" var="listContent">

                            <tr>


                                <td><s:property value="usernameaction"/></td>
                                <td><s:property value="description"/></td>
                                <td><s:property value="status"/></td>


                            </tr>


                        </s:iterator>


                        </tbody>
                    </table>


                </div>


            </div>
        </div>
    </div>


</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/functionsjquery.js"></script>


</body>
</html>
