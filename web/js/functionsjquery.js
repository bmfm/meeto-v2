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

var websocket = null;

$(function () { // URI = ws://10.16.0.165:8080/WebSocket/ws
    connect('ws://' + 'localhost:8080/meeto' + '/notifications');
    document.getElementById("chat").focus();
});

function connect(host) { // connect to the host websocket
    if ('WebSocket' in window)
        websocket = new WebSocket(host);
    else if ('MozWebSocket' in window)
        websocket = new MozWebSocket(host);
    else {
        writeToHistory('Get a real browser which supports WebSocket.');
        return;
    }

    websocket.onopen = onOpen; // set the event listeners below
    websocket.onclose = onClose;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
}

function onOpen(event) {
    writeToHistory('Connected to ' + window.location.host + '.');
    document.getElementById('chat').onkeydown = function (key) {
        if (key.keyCode == 13)
            doSend(); // call doSend() on enter key
    };
}

function onClose(event) {
    writeToHistory('WebSocket closed.');
    document.getElementById('chat').onkeydown = null;
}

function onMessage(message) { // print the received message
    writeToHistory(message.data);
}

function onError(event) {
    writeToHistory('WebSocket error (' + event.data + ').');
    document.getElementById('chat').onkeydown = null;
}

function doSend() {
    var message = document.getElementById('notifications').value;
    if (message != '')
        websocket.send(message); // send the message
    document.getElementById('notifications').value = '';
}

function writeToHistory(text) {
    var history = document.getElementById('notifications');
    var line = document.createElement('p');
    line.style.wordWrap = 'break-word';
    line.innerHTML = text;
    history.appendChild(line);
    history.scrollTop = history.scrollHeight;
}