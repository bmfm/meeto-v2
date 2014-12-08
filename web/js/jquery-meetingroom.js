$(function () {
    $('#addItemBtn').click(function () {


        $('#meetingroomcontainer').load('additem.jsp');

    });
});


$(function () {

    var id = $('#meetingidfromroom').val();
    $('#meetingidhidden').val(id);
    $('#meetingid').val(id);

});


