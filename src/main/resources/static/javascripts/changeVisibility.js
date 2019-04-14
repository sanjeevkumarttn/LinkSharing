$(document).on('change', '.newVisibility', function () {
    //alert("in visibility")
    var newVisibility = this.value;
    var topicId = $(this).attr("topic-id");
    console.log("topic id "+topicId);
    console.log("visibility "+newVisibility);

    var status = $.ajax({
        url : "/changeVisibility",
        data: {'newVisibility':newVisibility, 'topicId':topicId},
        method: "POST"
    });
    status.done(function (data) {
            console.log("done")
            $('body').load('/dashboard');
        }

    );
    status.failure(function () {
            console.log("Failure")
        }

    )
});