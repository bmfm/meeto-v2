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
    <link href="css/bootstrap-datetimepicker.css" rel="stylesheet" media="screen">


</head>


<body>

<div class="row clearfix">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="col-md-9 column">
                <s:form role="form">
                    <div class="form-group">
                        <table class="table table-striped">
                            <thead>
                            <tr>

                                <th>#</th>
                                <th>Title</th>
                                <th>Objective</th>
                                <th>Date</th>
                                <th>Location</th>
                                <th>Select</th>
                            </tr>
                            </thead>
                            <tbody>

                            <s:iterator value="list" status="status" var="listContent">

                                <tr>
                                    <td><s:property value="id"/></td>
                                    <td><s:property value="title"/></td>
                                    <td><s:property value="objective"/></td>
                                    <td><s:property value="date"/></td>
                                    <td><s:property value="location"/></td>
                                    <td><s:checkbox name="check" theme="simple" fieldValue="%{#listContent.id}"/></td>
                                </tr>
                            </s:iterator>

                            </tbody>
                        </table>
                        <s:hidden name="req" value="acceptInvitationRequest"/>
                        <s:submit id="acceptInviationsBtn" theme="simple" cssClass="btn btn-lg btn-primary btn-block"
                                  action="acceptInvitation"
                                  value="Accept Selected"/>
                        <s:submit id="declineInvitationsBtn" theme="simple" cssClass="btn btn-lg btn-primary btn-block"
                                  action="declineInvitation"
                                  value="Decline Selected"/>

                    </div>
                </s:form>

            </div>
        </div>
    </div>


</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/functionsjquery.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js"></script>


</body>
</html>
