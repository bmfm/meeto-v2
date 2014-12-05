$(function () {
    $('.btn-primary').click(function () {

        $('#pagecontainer').html('<img src="loading.gif" /> Now loading...');
        $.ajax({
            url: 'openMeetingDetails',
            data: $("#hiddenlabel").val(),
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