$(function () {
    $('#datetimepicker1').datetimepicker({
        format: 'dd-MM-yyyy hh:mm:ss',
        language: 'pt-BR'
    });
});


$(function () {
    $('#menuCreateMeeting').click(function () {
        $('#pagecontainer').load('create_meeting.jsp');
    });
});

$(function () {

    $('#menuListMeetings').click(function () {
        $('#pagecontainer').load('pending_invitations.jsp');
    });

});

$(function () {
    $('#itemShares').click(function () {
        $('#pagecontainer').load('SharesServlet?optionshares=1');
    });

});

$(function () {
    $('#itemTransactions').click(function () {
        $.get("TransactionsServlet", function (data) {
            $('#pagecontainer').html(data.replace(/\n/g, "<br>"));

        }, "text");
    });
});

$('document').ready(function () {
    $('#refreshCoins').click(function () {
        $.get("MoneyServlet", function (data) {
            $("#coins").text(data + "DEIcoins");
        }, "text");
    });
});

$(function () {
    $('#portfolio').click(function () {
        $('#pagecontainer').load('PortfolioServlet?');
    });
});

//mudar para hall of fame
$(function () {
    $('#itemHallofFame').click(function () {
        $('#pagecontainer').load('HallofFameServlet?');
    });
});

// root - comprar x ideia
$(function () {
    $('#buyallbtn').click(function () {

        alert("Entrou no jquery");

        var selected = $("#listideasroot").val().split("\t");

        alert("valor do selected:" + selected);

        alert("valor do selected[0]:" + selected[0]);

        $.get("RootServlet?ididearoot=" + selected[0], function (data) {
            alert("Chamou servlet e retornou alguma coisa");
            if ($.trim(data) == 'true') {
                alert("It's done Boss!");
                location.refresh();
            }
            else {
                alert("Ups...something went wrong..PLEASE DON'T HURT US!!");
                location.refresh();

            }
        }, "text");
    });

});


$('document').ready(function () {
    $('#itemNotifications').click(function () {
        $('#pagecontainer').load('notifications.jsp');
    });

});














                      





