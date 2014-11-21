/**
 * Created with IntelliJ IDEA.
 * User: brunomartins
 * Date: 03/12/13
 * Time: 23:32
 * To change this template use File | Settings | File Templates.
 */


$(function () {
    $("input,select,textarea,label,button").jqBootstrapValidation();
});

//SEARCH SPECIFIC IDEA
$('document').ready(function () {
    $('#submitsearchidea').click(function () {
        var ideatext = $("#ideafield").val();
        $.get("IdeasServlet?optionideas=2&ideatosearch=" + ideatext, function (data) {
            $('#ideaspagecontainer').html(data.replace(/\n/g, "<br>"));
        }, "text");
    });

});

//list all ideas
$('document').ready(function () {
    $('#listallideas').click(function () {
        $.get("IdeasServlet?optionideas=3", function (data) {
            $("#ideaspagecontainer").html(data.replace(/\n/g, "<br>"));
        }, "text");
    });

});

//delete idea
$('document').ready(function () {
    $('#deletebtn').click(function () {
        var selected = $("#ideiachoice").val().split(" ");
        $.get("IdeasServlet?optionideas=5&ideatodelete=" + selected[0], function (data) {
            if ($.trim(data) == 'true') {
                alert("Congratulations, you have deleted the idea");
                //falta redirect para nova pagina
            }
        }, "text");
    });
});

//add idea
$('document').ready(function () {
    $('#insertideabtn').click(function () {

        var ideadesc = $("#ideadesc").val();

        var filename = $("input[type=file]").val().replace(/(c:\\)*fakepath\\/i, '');

        var price = $("#ideaprice").val();

        var checked = $("input:checked");

        var ideatopic = checked.map(function () {
            return this.value
        }).get();


        if (ideatopic.length != 0 && ideadesc.length != 0 && price.length != 0) {
            $.get("IdeasServlet?optionideas=4&ideadesc=" + ideadesc + "&ideatopic=" + ideatopic + "&price=" + price + "&filename=" + filename, function (data) {


                if ($.trim(data) == 'true') {
                    alert("Idea inserted into our database!");
                    //falta redirect para nova pagina
                } else {
                    alert("Ooops! An error occurred. (You probably didn't have enough money)");
                }

            });

        } else {
            alert("You have to write a description, select at least one topic and have enough money");
        }

    });
});

//add to watchlist
$('document').ready(function () {
    $('#watchlistmodalbtn').click(function () {
        var selected = $("#watchlistchoice").val().split(" ");
        $.get("IdeasServlet?optionideas=6&watchlistoption=" + selected[0], function (data) {
            if ($.trim(data) == 'true') {
                alert("Congratulations, you have added the idea to your watchlist");
                window.location.reload();
            }
        }, "text");
    });
});

//search idea inside topic

$('document').ready(function () {
    $('#searchideainsidetopic').click(function () {
        var selected = $("#topicchoice").val().split(" ");
        $.get("IdeasServlet?optionideas=7&topicchoice=" + selected[0], function (data) {

            $('#ideaspagecontainer').html(data.replace(/\n/g, "<br>"));


        }, "text");
    });
});



