let button_login = document.getElementById('button_login')

//로그인
button_login.addEventListener('click', function(){
    let userId = document.getElementById('userId').value;
    let password = document.getElementById('password').value;
    let loginRequest = {
        userId : userId,
        password : password
    };
    console.log(loginRequest);
    sendFetchRequest(
        "/login",
        "POST",
        loginRequest,
        null,
        function(response) {
            console.log(response);
            // 응답 객체에서 userId와 accessToken 가져오기
            // 예시에서는 응답 데이터가 JSON 형태로 오고, 그 안에 userId와 accessToken이 포함되어 있다고 가정합니다.
            alert("로그인 성공");
            window.location.href = "/user";
        },
        function(error) {
            console.error(error);
        }
    );
})

