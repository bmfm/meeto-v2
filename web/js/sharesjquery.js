/**
 * Created with IntelliJ IDEA.
 * User: brunomartins
 * Date: 08/12/13
 * Time: 02:11
 * To change this template use File | Settings | File Templates.
 */

//change price

$('document').ready(function () {
    $('#setpricebtn').click(function () {
        var selected = $("#ideapricechoice").val().split(" ");
        var newprice = $("#newprice").val();
        $.get("SharesServlet?optionshares=2&ideatochange=" + selected[0] + "&newprice=" + newprice, function (data) {
            if ($.trim(data) == 'true') {
                alert("Congratulations, you have changed the price!");
                window.location.reload();
            }
        }, "text");
    });

});

//check price
$('document').ready(function () {
    $('#checkcurrentpricebtn').click(function () {
        $.get("SharesServlet?optionshares=3", function (data) {
            if ($.trim(data.length) != 0) {
                alert("This idea is currently sold at " + data + " DEICoins each");

            }
        }, "text");
    });

});

//Call the Servlet to load shares.sub into the page
$('document').ready(function () {
    $('#checkideabtn').click(function () {
        var selected = $("#ideiachoice").val().split(" ");
        $('#forsharessub').load('SharesServlet?optionshares=4&checkidea=' + selected[0]);
    });
});


//buy order
$('document').ready(function () {
    $('#buybtn').click(function () {
        var selectedshare = $("#personchoice").val().split("\t");
        var quantity = $("#sharequantity").val();


        $.get("SharesServlet?optionshares=5&person=" + selectedshare[3] + "&quantity=" + quantity + "&ididea=" + selectedshare[0], function (data) {
            if ($.trim(data) == 'Compra efectuada com sucesso!') {
                alert("Congratulations, you have bought some shares!");
                //falta redirect para pagina
            } else {
                alert("Error:" + $.trim(data));

            }

        }, "text");
    });

});



