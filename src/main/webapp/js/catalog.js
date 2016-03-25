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

        }
    });
});

function postRating(bookId, rating) {
    $.post(
            $('#ratingPostUrl').val(),
            {
                book: bookId,
                rate: rating
            }
    )
}
