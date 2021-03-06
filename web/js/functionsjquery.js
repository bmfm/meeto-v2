//open create meeting jsp
$(function () {
    $('#menuCreateMeeting').click(function () {

        $('#pagecontainer').html('<img src="loading.gif" /> Now loading...');

        $.ajax({
            url: 'openCreateMeeting',
            type: 'POST',
            dataType: 'html',
            success: function (html) {
                $('#pagecontainer').html(html);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert('An error occurred! ' + thrownError);
            }
        });
    });
});


//open invitations.jsp - pending invitations
$(function () {
    $('#menuPendingInvitations').click(function () {

        $('#pagecontainer').html('<img src="loading.gif" /> Now loading...');


        $.ajax({
            url: 'openInvitations',
            type: 'POST',
            dataType: 'html',
            success: function (html) {
                $('#pagecontainer').html(html);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert('An error occurred! ' + thrownError);
            }
        });
    });
});

// open meetings.jsp - list meetings / meetings overview
$(function () {
    $('#menuListMeetings').click(function () {

        $('#pagecontainer').html('<img src="loading.gif" /> Now loading...');
        $.ajax({
            url: 'openMeetings',
            type: 'POST',
            dataType: 'html',
            success: function (html) {
                $('#pagecontainer').html(html);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert('An error occurred! ' + thrownError);
            }
        });
    });
});


//Enter 'join meeting' menu
$(function () {
    $('#menuJoinMeeting').click(function () {

        $('#pagecontainer').html('<img src="loading.gif" /> Now loading...');
        $.ajax({
            url: 'listMyMeetings',
            type: 'POST',
            dataType: 'html',
            success: function (html) {
                $('#pagecontainer').html(html);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert('An error occurred! ' + thrownError);
            }
        });
    });
});

$(function () {
    $('#menuTodoList').click(function () {

        $('#pagecontainer').html('<img src="loading.gif" /> Now loading...');
        $.ajax({
            url: 'openTodoList',
            type: 'POST',
            dataType: 'html',
            success: function (html) {
                $('#pagecontainer').html(html);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert('An error occurred! ' + thrownError);
            }
        });
    });
});


//set datetimepicker preferenes
$(function () {
    $('.form_datetime').datetimepicker({
        format: "yyyy-MM-dd hh:ii",
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0

    });
});
