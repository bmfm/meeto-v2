$(function () {
    $('#addItemBtn').click(function () {
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