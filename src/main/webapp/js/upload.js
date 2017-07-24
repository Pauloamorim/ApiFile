$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        maxChunkSize: 1000000 // 1 MB
    });
});