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
    <link href="css/style.css" rel="stylesheet" media="screen">
    <link href="css/signin.css" rel="stylesheet">
    <title>Register</title>


</head>

<body>

<div class="container">

    <s:form cssClass="form-signin" action="register" method="post">
        <h2 class="form-signin-heading">Please register</h2>
        <input type="text" name="userName" class="form-control" placeholder="Username" required autofocus>
        <input type="password" name="password" class="form-control" placeholder="Password" required>
        <input type="password" name="repeatpassword" class="form-control" placeholder="Repeat password" required>
        <input type="text" name="mail" class="form-control" placeholder="E-mail" required>


        <button class="btn btn-lg btn-primary btn-block" data-loading-text="Loading..." type="submit">Register</button>
        <button class="btn btn-default btn-lg btn-block" type="button" onClick="window.history.back()">Back</button>

    </s:form>

</div>
<!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script src="${pageContext.request.contextPath}/js/jquery.js" type="javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js" type="javascript"></script>
</body>
</html>
