$(function () {
    $('#addItemBtn').click(function () {


        $('#meetingroomcontainer').load('additem.jsp');

    });
});


$(function () {

    var id = $('#meetingidfromroom').val();

    $('#meetingid').val(id);


});


