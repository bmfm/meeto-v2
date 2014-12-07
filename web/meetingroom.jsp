<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

    <title>meetingroom</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">

</head>


<body>

<div class="container">
    <div class="row clearfix">
    <div class="panel panel-default">
        <div class="panel-body">


            <div class="col-md-12 column">

                <button id="closeMyMeeting" onclick="window.close()" class="btn btn-primary">CLOSE</button>

                <h3>Agenda items</h3>

                <s:hidden name="meetingidfromroom" value="%{joinmymeetingid}"/>



                <table class="table table-striped">
                    <thead>
                    <tr>

                        <th>#</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Key Decision</th>

                    </tr>
                    </thead>
                    <tbody>

                    <s:iterator value="agendaItemsList" status="status" var="listContent">
                        <tr>
                            <td><s:property value="id"/></td>
                            <td><s:property value="itemname"/></td>
                            <td><s:property value="itemdescription"/></td>
                            <td><s:property value="keydecision"/></td>

                            <td><s:form role="form" theme="simple">
                                <s:hidden name="agendaItemID" value="%{#listContent.id}"/>
                                <s:hidden name="req" value="hiddenreq"/>

                                <s:submit id="modifyItemBtn" theme="simple" cssClass="btn btn-primary"
                                          action="modifyItem"
                                          value="Modify"/>

                                <s:submit id="deleteItemBtn" theme="simple" cssClass="btn btn-primary"
                                          action="deleteItem"
                                          value="Delete"/>

                                <s:submit id="addKeyDecisionBtn" theme="simple" cssClass="btn btn-primary"
                                          action="addKeyDecision"
                                          value="Add Key Decision"/>

                                <s:submit id="assignTaskBtn" theme="simple" cssClass="btn btn-primary"
                                          action="assignTask"
                                          value="Assign Task"/>

                            </s:form>
                            </td>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>

                <button type="submit" id="addItemBtn" class="btn btn-primary">ADD</button>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-body" id="meetingroomcontainer">

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

</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-meetingroom.js"></script>


</body>
</html>
