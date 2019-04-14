$(document).on('focusout', '#confirmPassword', function(){
        var password = document.getElementById("password").value;
        var cnfPassword = document.getElementById("confirmPassword").value;

        if(password == cnfPassword)
            return true;
        else {
            document.getElementById("password").value = null;
            document.getElementById("confirmPassword").value = null;
            alert("Both password should be same");
            return false;
        }
});

$(document).on('focusout', '#email', function(){

    var email = this.value;
    var check = $.ajax({
        url: "/isEmailRegistered",
        data: {"email":email},
        method:"GET"

    });
    check.done(function (data) {
        console.log("in check")
        if(data){
            document.getElementById("email").value = null;
            alert("Email is already registered...")
            return false;
        }
        else{
            return true;
        }
    })

});


$(document).on('focusout', '#userName', function(){

    var userName = this.value;
    var check = $.ajax({
        url: "/isUserNameRegistered",
        data: {"userName":userName},
        method:"GET"

    });
    check.done(function (data) {

        if(data){
            document.getElementById("userName").value = null;
            alert("Username is already registered...")
            return false;
        }
        else{
            return true;
        }
    })

});

$(document).on('focusout', '#topicName', function () {
    var topicname = this.value;
    console.log(topicname);
    var check = $.ajax({
        url: "/checkTopicNameUnique",
        data: {"topicname": topicname},
        method: "GET"
    });
    check.done(function (data) {
        if (!data) {
            document.getElementById('topicName').value = null;
            //$('#tname-msg').text("topic already exists");
            alert("topic already exists");
            console.log(data);
        }

    });

});
