<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <title>Modify Item Description</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">
</head>
<body>

<div class="col-md-9 column">

    <s:form role="form" class="col-md-12">
        <s:hidden name="req" value="modifyItem"/>
        <s:hidden name="itemid" value="%{agendaItemID}"/>

        <div class="form-group">
            <label for="itemdescription">Item Description</label>
            <input class="form-control" name="itemdescription" id="itemdescription" type="text" required/>

        </div>

        <s:submit id="modifyItemDescriptionBtn" theme="simple" cssClass="btn btn-primary"
                  action="modifyItemDescription"
                  value="Modify Description"/>

    </s:form>
    <p>&nbsp;</p>
</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-meetingroom.js"></script>

</body>
</html>
