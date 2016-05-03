$(function(){

    $(document).ready(function() {
        publishTagReference();
        addBookHandler();
    });
});

function publishTagReference() {
    $('#book-tags').multiselect();

    var url = $('#tagsGetUrl').val();
    $.getJSON(url, {}, function(json) {
        var tags = json;

        var data = [];
        tags.forEach(function(item) {
            data.push({
                label: item.tagName,
                title: item.tagName,
                value: item.id
            })
        });

        $('#book-tags').multiselect('dataprovider', data);
    });
}

function addBookHandler() {
    $('#addBookBtn').on('click', function() {
        var tags = [];

        $.each($('#book-tags').find('option:selected'), function(){
            var tagId = $(this).val();
            tags.push({id: tagId});
        });

        var book = {
            bookName: $('#bookName').val(),
            authorLastName: $('#authorLastName').val(),
            authorFirstName: $('#authorFirstName').val(),
            authorPatronymic: $('#authorPatronymic').val(),
            bookYear: $('#bookYear').val(),
            bookAnnotation: $('#bookAnnotation').val(),
            tags: tags
        };

        var url = $('#postBookUrl').val();
        $.ajax(url, {
            type: "POST",
            async: false,
            dataType: "json",
            data: JSON.stringify(book),
            contentType: 'application/json'
        });
    })
}