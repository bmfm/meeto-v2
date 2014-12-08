<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <title>addkeydecision</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">
</head>


<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-9 column">

            <s:form role="form" class="col-md-12" action="addKeyDecision" theme="simple">
                <s:hidden name="req" value="addKeyDecision"/>

                <s:hidden name="agendaitemidhidden" id="agendaitemidhidden" value="%{agendaItemID}"/>

                <div class="form-group">
                    <label for="keydecision">Key Decision</label>
                    <input class="form-control" name="keydecision" id="keydecision" type="text" required/>

                </div>

                <button type="submit" id="submitKeyDecisionBtn" class="btn btn-primary">Add Key Decision</button>
                <button id="closeaddKeyDecision" onclick="window.close()" class="btn btn-primary">CLOSE</button>

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


    </div>
</div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>


</body>
</html>
