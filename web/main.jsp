<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title>IDEABroker</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="css/bootstrap.css" rel="stylesheet" media="screen">


    <%--<script language="javascript" type="text/javascript">
        <%Boolean logged = (Boolean) session.getAttribute("logged_in");%>
        if (<%=logged%>) {

        } else {
            alert("Invalid access. Please log in.");
            window.location.replace("login.jsp");

        }
    </script>--%>

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

                        <li><a href="<%=session.getAttribute("fbURL")%>" id="fbloginlabel">Facebook login</a></li>


                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li>

                            <p class="navbar-text">Logged in as ${session.username} <a id="portfolio" href="#"
                                                                                       class="navbar-link">
                            </a></p>
                        </li>
                        <li>
                            <%--(String) session.getAttribute("cash") didn't work here...wtf.--%>
                            <%String cash = String.valueOf(session.getAttribute("cash"));%>
                            <p class="navbar-text"><a id="coins"><%=cash%> DEIcoins</a></p>

                        </li>
                        <li>
                            <p class="navbar-text"><a id="refreshCoins" href="#" class="navbar-link">Refresh Coins</a>
                            </p>

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
        <div class="col-md-2 column">
            <div class="tabbable">
                <ul class="nav nav-pills nav-stacked">
                    <li class="active"><a id="itemTopics" href="#" data-toggle="tab">Topics</a></li>
                    <li><a id="itemIdeas" href="#" data-toggle="tab">Ideas</a></li>
                    <li><a id="itemShares" href="#" data-toggle="tab">Shares</a></li>
                    <li><a id="itemTransactions" href="#" data-toggle="tab">Transactions</a></li>
                    <li><a id="itemHallofFame" href="#" data-toggle="tab">Hall of Fame</a></li>
                    <li><a id="itemNotifications" href="#" data-toggle="tab">Notifications</a></li>

                </ul>
            </div>
        </div>
        <div class="col-md-10 column" id="pagecontainer">
            Welcome! Please select one of the options on the left.


        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/functionsjquery.js"></script>


</body>

</html>
