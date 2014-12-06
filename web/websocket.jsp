<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><!DOCTYPE html>
<html>
<head>
    <title>WebSocket Chat</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript">

    </script>
</head>
<body>
<noscript>JavaScript must be enabled for WebSockets to work.</noscript>
<div>
    <div id="container">
        <div id="history"></div>
    </div>
    <p><input type="text" placeholder="type to chat" id="chat"></p>
</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-websockets.js"></script>

</body>
</html>