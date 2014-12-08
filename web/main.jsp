<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Meeto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="css/bootstrap.css" rel="stylesheet" media="screen">


</head>

<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <nav class="navbar navbar-default" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1"><span
                            class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
                            class="icon-bar"></span><span class="icon-bar"></span></button>
                    <a class="navbar-brand" href="">Meeto</a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">

                        <%--<li><a href="<%=session.getAttribute("fbURL")%>" id="fbloginlabel">Facebook login</a></li>--%>


                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li>

                            <p class="navbar-text">Logged in as ${session.username}</p>
                        </li>

                        <li>

                            <s:if test="%{#session.googleid == null}">
                                <p class="navbar-text"><a href="<s:url action="goToGoogleLogin"/>" class="navbar-link">Associate
                                    with Google</a></p>

                            </s:if>
                            <s:else><p class="navbar-text">Google ID: ${session.googleid}</p></s:else>


                        </li>

                        <li>
                            <p class="navbar-text"><a href="<s:url action="logout"/>" class="navbar-link">Logout</a>
                            </p>
                        </li>
                        <li>


                        </li>

                    </ul>
                </div>

            </nav>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-3 column">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Menu</h3>
                </div>
                <div class="panel-body">

                    <div class="tabbable">
                        <ul class="nav nav-pills nav-stacked">
                            <li class="active"><a id="menuCreateMeeting" href="#" data-toggle="tab">Create Meeting</a>
                            </li>
                            <li><a id="menuListMeetings" href="#" data-toggle="tab">List meetings</a></li>
                            <li><a id="menuPendingInvitations" href="#" data-toggle="tab">Pending Invitations</a></li>
                            <li><a id="menuJoinMeeting" href="#" data-toggle="tab">Join meeting</a></li>
                            <li><a id="websockettest" href="#" data-toggle="tab">Websocket test</a></li>


                        </ul>
                    </div>
                </div>
            </div>

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Notification Area</h3>
                </div>
                <div class="panel-body">

                    <div class="tabbable" id="notifications">

                    </div>
                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">Users Online</h3>
                </div>
                <div class="panel-body">

                    <div class="tabbable" id="usersonline">

                    </div>
                </div>
            </div>


        </div>

        <div class="col-md-9 column" id="pagecontainer">

            <div class="panel panel-default">
                <div class="panel-body">

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
                    <s:else>Welcome! Please select one of the options on the left.</s:else>
                </div>
            </div>


        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/functionsjquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>


</body>

</html>
