<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">

    <link href="css/signin.css" rel="stylesheet">
    <title>Log in</title>


</head>

<body>

<div class="container">

    <s:form action="login.action" method="post" cssClass="form-signin" focusElement="user" >
        <h2 class="form-signin-heading">Please sign in</h2>
        <s:textfield id="user" name="userName" cssClass="form-control" placeholder="Username" required="true"/>
        <s:password name="password" cssClass="form-control" placeholder="Password" required="true"/>

        <!-- stupid data-loading-text isn't working... -->
        <s:submit cssClass="btn btn-lg btn-primary btn-block" type="submit">Sign in</s:submit>
        <button class="btn btn-default btn-lg btn-block" type="button" onClick="window.history.back()">Back</button>
    </s:form>

</div>
<!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script src="js/jquery.js" type="javascript"></script>
<script src="js/bootstrap.js" type="javascript"></script>
</body>
</html>
