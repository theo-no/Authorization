let button_user_info = document.getElementById('button_user_info')

//로그인
button_user_info.addEventListener('click', function(){
    sendFetchRequest(
        "/user/info",
        "GET",
        null,
        null,
        function(response) {
            console.log(response);
        },
        function(error) {
            console.error(error);
        }
    );
})

