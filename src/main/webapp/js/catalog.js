var bookModel = {
    books: []
};

//var rivetsBookModelBinder;

$(function(){

    $(document).ready(function() {
        //initBooks();

        var $rating = $('.rating');
        $rating.each(function () {
            $('<span class="label label-default"></span>')
                .text($(this).val() || ' ')
                .insertAfter(this);
        });
        $rating.on('change', function () {
            var rate = $(this).val();
            $(this).next('.label').text(rate);
            var bookId = $(this).parents('.item').get(0).getAttribute('bookid');
            postRating(bookId, rate);
        });

        //rivetsBookModelBinder = rivets.bind($('#bookStore'), bookModel);
    });
});

/*function initBooks() {
    $.get(
        $('#bookListDataUrl').val(),
        function(data) {
            bookModel.books = data;
        });
}*/

function postRating(bookId, rating) {
    $.post(
            $('#ratingPostUrl').val(),
            {
                book: bookId,
                rate: rating
            }
    )
}