$(document).ready(function() {
    $('#findBest').click(function() {
        $('#message').text("Finding best restaurant...");
        $.ajax({
            url:'api/testConsumer/findBestRestaurant',
            contentType:"application/json; charset=utf-8",
            dataType: 'json',
            type: 'GET'
        }).done(function(data) {
            $('#message').text("Found.");
            $('#result').val(data.result);
        }).fail(function( jqXHR, textStatus, errorThrown) {
            $('#message').text("Error");
            console.log('always.'+textStatus);
        });
    });

    $('#postBest').click(function() {
        $('#message').text("Posting...");
        $.ajax({
            url:'api/testConsumer/postBestRestaurant?bestRestaurant='+encodeURIComponent($('#result').val()),
            contentType:"application/json; charset=utf-8",
            dataType: 'json',
            type: 'GET'
        }).done(function(data) {
            $('#message').text("Posted");
        }).fail(function( jqXHR, textStatus, errorThrown) {
            $('#message').text("Error");
            console.log('always.'+textStatus);
        });
    });
});
