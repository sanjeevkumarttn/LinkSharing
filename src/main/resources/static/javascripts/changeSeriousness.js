$(document).on('change', '.newseriousness', function () {

    var newseriousness = this.value;
    var topicId = $(this).attr("topic-id");
    var status = $.ajax({
        url : "/changeSeriousness",
        data: {'newseriousness':newseriousness, 'topicId':topicId},
        method: "POST"
    });

   status.done(function (data) {
            console.log("done")
        }

    );


});