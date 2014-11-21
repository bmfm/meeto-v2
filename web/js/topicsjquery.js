/**
 * Created with IntelliJ IDEA.
 * User: brunomartins
 * Date: 01/12/13
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */

$('document').ready(function () {
    $('#listalltopics').click(function () {
        $.get("TopicsServlet?option=1", function (data) {
            $("#topicspagecontainer").html(data.replace(/\n/g, "<br>"));
        }, "text");
    });

});

$('document').ready(function () {
    $('#submitsearch').click(function () {
        var text = $("#topicfield").val();
        $.get("TopicsServlet?option=2&topictosearch=" + text, function (data) {


            // tab replacement needs tweaking

            $("#topicspagecontainer").html(data.replace(/\n/g, "<br>"));
        }, "text");
    });

});


