<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Bruno Martins">
    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">
    <link href="css/signin.css" rel="stylesheet">
    <title>Welcome to Meeto v0.1</title>

</head>

<body>

<div class="jumbotron">
    <div class="container">
        <h1>Welcome to Meeto v0.1!</h1>

        <p>Please select one of the options below to begin your journey</p>

        <p><a class="btn btn-primary btn-lg" onClick="parent.location='login.jsp'">Login</a>
            <a class="btn btn-primary btn-lg" onClick="parent.location='register.jsp'">Register</a>
            <a class="btn btn-primary btn-lg" onClick="">Google Calendar</a>

        </p>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/jquery.js" type="javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js" type="javascript"></script>
</body>
</html>
