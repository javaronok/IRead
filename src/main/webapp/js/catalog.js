
$(function(){

    $(document).ready(function() {
        var $rating = $('.rating');
        $rating.each(function () {
            $('<span class="label label-default"></span>')
                .text($(this).val() || ' ')
                .insertAfter(this);
        });
        $rating.on('change', function () {
            $(this).next('.label').text($(this).val());
        });
    });
});