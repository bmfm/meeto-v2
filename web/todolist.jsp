<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

    <title>Todo List</title>
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
                <h2>Here's a list of your tasks:</h2>

                <div class="form-group">
                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th>#</th>
                            <th>Action Description</th>
                            <th>Status</th>
                            <th>Options</th>

                        </tr>
                        </thead>
                        <tbody>

                        <s:iterator value="todolist" status="status" var="listContent">
                            <tr>
                                <td><s:property value="id"/></td>
                                <td><s:property value="description"/></td>
                                <td><s:property value="status"/></td>

                                <td>
                                    <s:form role="form" theme="simple" id="formCompleteTask" action="completeTask">

                                        <s:hidden name="idmeeting" value="%{#listContent.id}"/>
                                        <s:hidden name="req" value="hiddenreq"/>


                                        <s:submit theme="simple" cssClass="btn btn-primary"
                                                  value="Mark as Done"/>

                                    </s:form>
                                </td>

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


</body>
</html>
