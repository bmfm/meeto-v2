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


$(function () {

    $('#websockettest').click(function () {
        $('#pagecontainer').load('websocket.jsp');
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
    $('#deleteItemBtn').submit(function () { // catch the form's submit event
        $.ajax({ // create an AJAX call...
            data: $(this).serialize(), // get the form data
            type: $(this).attr('POST'), // GET or POST
            url: $(this).attr('deleteItem'), // the file to call
            success: function (response) { // on success..
                $('#pagecontainer').html(response); // update the DIV
            }
        });
        return false; // cancel original event to prevent form submitting
    });
});


/*$(document).on('submit', '#formEnterMeeting', function(e) {
 $.ajax({
 url: $(this).attr('/meeto/openMeetingRoom.action'),
 type: $(this).attr('POST'),
 data: $(this).serialize(),
 success: function(html) {
 $('#pagecontainer').html(html)

 }
 });
 e.preventDefault();
 });*/

/*$(function () {
 $('#formEnterMeeting').submit(function () { // catch the form's submit event
        $.ajax({ // create an AJAX call...
            data: $(this).serialize(), // get the form data
            type: $(this).attr('POST'), // GET or POST
            url: $(this).attr('/meeto/openMeetingRoom.action'), // the file to call
 dataType: 'html',
 success: function (html) { // on success..
 $('#pagecontainer').html(html); // update the DIV
 },
            error: function (xhr, ajaxOptions, thrownError) {
                alert('An error occurred! ' + thrownError);
            }
        });
        return false; // cancel original event to prevent form submitting
    });
 });*/


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