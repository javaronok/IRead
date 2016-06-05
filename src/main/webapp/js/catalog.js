$(function(){

    $(document).ready(function() {
        var isAuthed = $('#isAuthed').val() === "true";

        $('.item').each(function () {
            var r = parseFloat($(this).find('.avg-rating').text());
            $(this).find('.avg-rating').text(r.toFixed(2) || ' ');
        });

        if (isAuthed) {
            var $rating = $('.rating').rating();

            $rating.each(function () {
                var r = parseFloat($(this).val());
                $('<span class="label label-default"></span>').text(r.toFixed(2) || ' ').insertAfter(this);
            });

            $rating.on('change', function () {
                var rate = $(this).val();
                $(this).next('.label').text(rate);
                var bookId = $(this).parents('.item').get(0).getAttribute('bookid');
                postRating(bookId, rate);
            });

            $('.btn-del').on('click', function () {
                var bookId = $(this).parents('.item').get(0).getAttribute('bookid');
                deleteBook(bookId);

                window.location.reload();
            });
        }
    });
});

function postRating(bookId, rating) {
    $.ajax({
        type: "POST",
        url: $('#ratingPostUrl').val(),
        dataType: "json",
        data:{
            book: bookId,
            rate: rating
        }
    });
}

function deleteBook(bookId) {
    $.ajax({
        type: "POST",
        url: $('#deleteBookUrl').val(),
        dataType: "json",
        data:{book: bookId}
    });
}