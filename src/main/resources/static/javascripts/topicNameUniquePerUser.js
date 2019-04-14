$(document).on('focusout', '#topicName', function () {
    var topicname = this.value;
    console.log(topicname);
    var check = $.ajax({
        url: "/checkTopicNameUnique",
        data: {"topicName": topicname},
        method: "GET"
    });
    check.done(function (data) {
        if (!data) {
            document.getElementById('topicName').value = null;
            alert("topic already exists")
            console.log(data);
        }

    });

});

