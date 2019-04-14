
$(document).on('focusout', '#userName', function () {
    var uname = this.value;
    console.log(uname);
    var check = $.ajax({
        url: "/checkUsernameAvailabilityToEdit",
        data: {"uname": uname},
        method: "GET"
    });
    check.done(function (data) {
        if (data) {
            document.getElementById('userName').value = null;
            //$('#uname-msg').text("Username already exists");
            alert("Username already exists")
            console.log(data);
        }

    });

});


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
