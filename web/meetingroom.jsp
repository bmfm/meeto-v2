<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
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
            <div class="col-md-11 column" id="meetingsmaindiv">


                <table class="table table-striped">
                    <thead>
                    <tr>

                        <th>#</th>
                        <th>Meeting Description</th>
                        <th>Objective</th>
                        <th>Date</th>
                        <th>Location</th>
                        <th>Details</th>
                    </tr>
                    </thead>
                    <tbody>

                    <s:iterator value="allMeetings" status="status" var="listContent">


                        <tr>


                            <td><s:property value="id"/></td>
                            <td><s:property value="title"/></td>
                            <td><s:property value="objective"/></td>
                            <td><s:property value="date"/></td>
                            <td><s:property value="location"/></td>
                            <td><s:form role="form" theme="simple" id="formViewDetails" target="meetingdetails"
                                        action="openMeetingDetails">
                                <s:hidden name="meetingOverviewID" value="%{#listContent.id}"/>
                                <s:hidden name="req" value="hiddenreq"/>
                                <%--<td><button id="sta" class="btn btn-primary">View</button></td>--%>

                                <sj:submit theme="simple" id="viewDetails" cssClass="btn btn-primary" value="View"/>

                            </s:form>
                            </td>


                        </tr>


                    </s:iterator>

                    </tbody>
                </table>


            </div>


        </div>

    </div>

    <div class="panel panel-default">
        <div class="panel-body" id="meetingdetails">


        </div>
    </div>


</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/functionsjquery.js"></script>


</body>
</html>
