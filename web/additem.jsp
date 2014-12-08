<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <title>additem</title>
</head>
<body>

<div class="col-md-9 column">

    <s:form role="form" class="col-md-12" action="addItem">
        <s:hidden name="req" value="addItem"/>
        <s:hidden name="meetingidhidden" id="meetingidhidden"/>

        <div class="form-group">
            <label for="meetingid">Meeting ID</label>
            <input class="form-control" name="meetingid" id="meetingid" type="text" disabled required/>

        </div>

        <div class="form-group">
            <label for="itemname">Item name</label>
            <input class="form-control" name="itemname" id="itemname" type="text" required/>

        </div>

        <div class="form-group">
            <label for="itemdescription">Item Description</label>
            <textarea class="form-control" name="itemdescription" id="itemdescription" rows="2"
                      cols="2" required></textarea>
        </div>

        <button type="submit" id="submitAddItemBtn" class="btn btn-primary">Add item</button>

    </s:form>
    <p>&nbsp;</p>
</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-meetingroom.js"></script>

</body>
</html>
