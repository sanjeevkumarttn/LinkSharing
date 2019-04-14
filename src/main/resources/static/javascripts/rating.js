$(document).on('click','.star',function (){
    //alert("dgeuij")
    $(document).on('click', '.star', function () {
        var num = parseInt($(this).attr('value'));
        var grandParentId = ($(this).parent().parent().attr('id'));
        //console.log(grandParentId);
        $('.star' + grandParentId).removeClass("glyphicon glyphicon-heart").addClass("glyphicon glyphicon-heart-empty");
        for (i = 1; i <= num; i++) {
            //console.log(i);
            $('#' + grandParentId).find('#' + i).removeClass("glyphicon glyphicon-heart-empty").addClass("glyphicon glyphicon-heart");
        }

        var status = $.ajax({
            url: "/resourceRating",
            data: {'rating': num, 'resourceId': grandParentId},
            method: "POST"
        });

        status.done(function () {
                console.log("done")
            }
        );
    });
    }
);