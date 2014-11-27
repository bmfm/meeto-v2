<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.min.css" rel="stylesheet" media="screen">

    <title>topics</title>


<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">

            <div class="col-md-6">
                <form class="form-search" role="search">
                    <div class="input-group">
                        <input type="text" id="topicfield" class="form-control">
                            <span class="input-group-btn">
                            <button type="button" id="submitsearch" class="btn btn-default"><i
                                    class="glyphicon glyphicon-search"></i></button>
                            </span>
                    </div>
                </form>
            </div>
            <div class="col-md-6">

                <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                    Add topic
                </button>
                <!-- Modal -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">Please insert new topic</h4>
                            </div>
                            <div class="modal-body">

                                <form role="form" method="post" action="/ProjectSD2/TopicsServlet">
                                    <div class="form-group">
                                        <label for="topic">Topic</label>
                                        <input type="text" class="form-control" id="topic" name="newtopic"
                                               maxlength="50" placeholder="Enter new topic">
                                    </div>

                                    <button type="submit" class="btn btn-primary">Submit</button>
                                </form>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->

                <button class="btn btn-primary" id="listalltopics">
                    List All
                </button>


            </div>


        </div>
    </div>
    <p>&nbsp;</p>

    <div class="row clearfix">
        <div class="col-md-12 column" id="topicspagecontainer">


        </div>
    </div>
</div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/topicsjquery.js"></script>

</body>
</html>
