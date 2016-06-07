$(function(){
    $(document).ready(function() {
        publishTables();
    });
});


function publishTables() {
    initRatingsTable();
    initCompositeRecommendationTable();
    initCbfRecommendationTable();
    initCollaborativeRecommendationTable();
}


function initRatingsTable() {
    return $('#ratingsTable').dataTable({
        "sDom": "t",
        "bServerSide": false,
        "bSort": true,
        "bAutoWidth": false,
        "pageLength": 100,
        "order": [[ 0, "asc" ]],
        "sAjaxSource": $('#ratingsTableUrl').val(),
        "columns": [
            {"data": "book.bookName"},
            {"data": "book.authorName"},
            {"data": "book.tagList"},
            {
                "data": "avgRating",
                "sClass": "alignRight",
                "width": "115px"
            },
            {
                "data": "ownRating",
                "sClass": "alignRight",
                "width": "115px"
            }
        ]
    });
}

function initCompositeRecommendationTable() {
    return $('#compositeTable').dataTable({
        "sDom": "t",
        "bServerSide": false,
        "bSort": true,
        "bAutoWidth": false,
        "pageLength": 100,
        "order": [[4, "desc"]],
        "sAjaxSource": $('#compositeTableUrl').val(),
        "columns": [
            {"data": "book.bookName"},
            {"data": "book.authorName"},
            {"data": "book.tagList"},
            {
                "data": "avgRating",
                "sClass": "alignRight",
                "width": "125px"
            },
            {
                "data": "score",
                "sClass": "alignRight",
                "width": "125px"
            }
        ]
    });
}

function initCbfRecommendationTable() {
    return $('#cbfTable').dataTable({
        "sDom": "t",
        "bServerSide": false,
        "bSort": true,
        "bAutoWidth": false,
        "pageLength": 100,
        "order": [[4, "desc"]],
        "sAjaxSource": $('#cbfTableUrl').val(),
        "columns": [
            {"data": "book.bookName"},
            {"data": "book.authorName"},
            {"data": "book.tagList"},
            {
                "data": "avgRating",
                "sClass": "alignRight",
                "width": "125px"
            },
            {
                "data": "score",
                "sClass": "alignRight",
                "width": "125px"
            }
        ]
    });
}

function initCollaborativeRecommendationTable() {
    return $('#collabrTable').dataTable({
        "sDom": "t",
        "bServerSide": false,
        "bSort": true,
        "bAutoWidth": false,
        "pageLength": 100,
        "order": [[ 4, "desc" ]],
        "sAjaxSource": $('#collaborativeTableUrl').val(),
        "columns": [
            {"data": "book.bookName"},
            {"data": "book.authorName"},
            {"data": "book.tagList"},
            {
                "data": "avgRating",
                "sClass": "alignRight",
                "width": "125px"
            },
            {
                "data": "score",
                "sClass": "alignRight",
                "width": "125px"
            }
        ]
    });
}