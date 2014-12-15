<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

    <title>meetings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">
    <link href="css/bootstrap-datetimepicker.css" rel="stylesheet" media="screen">


</head>


<body>

<div class="row clearfix">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="col-md-9 column">


                <s:form role="form" class="col-md-8" action="createMeeting">
                    <div class="form-group">
                        <label for="meetingTitle">Meeting Title</label><input class="form-control" name="meetingTitle"
                                                                              id="meetingTitle"
                                                                              type="text" required/>
                        <s:hidden name="req" value="createMeetingHidden"/>
                    </div>
                    <div class="form-group">
                        <label for="desiredOutcome">Desired Outcome</label><textarea class="form-control"
                                                                                     name="desiredOutcome"
                                                                                     id="desiredOutcome" rows="3"
                                                                                     cols="3" required></textarea>
                    </div>

                    <div class="form-group">

                        <div class="input-group date form_datetime" data-date="2014-12-25T10:00:07Z"
                             data-date-format="dd-MM-yyyy HH:ii p" data-link-field="dtp_input1">

                            <input class="form-control" type="text" id="datetime" value="" readonly required>


                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                        <input type="hidden" name="datetime" id="dtp_input1" value=""/><br/>
                    </div>
                    <div class="form-group">
                        <label for="location">Location</label><input class="form-control" name="location" id="location"
                                                                     type="text" required/>
                    </div>
                    <div class="form-group">
                        <label>Members</label>

                        <s:checkboxlist name="checkboxes" list="list" theme="simple"/>

                        <p class="help-block">
                            Select members to invite
                        </p>
                    </div>


                    <button type="submit" id="submitMeetingBtn" class="btn btn-primary">Submit</button>

                </s:form>
                <p>&nbsp;</p>

            </div>
        </div>
    </div>


</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="js/functionsjquery.js"></script>


</body>
</html>
