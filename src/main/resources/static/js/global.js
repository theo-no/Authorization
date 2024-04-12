
//API 통신 호출 함수
function sendFetchRequest(url, type, requestData, params, successCallback, errorCallback) {
    const options = {
        method: type,
        headers: {
            "Content-Type": "application/json"
        },
        body: requestData ? JSON.stringify(requestData) : null
    };
    if (params) {
        url += '?' + new URLSearchParams(params).toString();
    }
    fetch(url, options)
        .then(response => {
            if (response.ok) {
                const contentType = response.headers.get("Content-Type");
                if (contentType && contentType.includes("application/json")) {
                    return response.json().then(json => successCallback(response, json)); // JSON 형태일 때
                } else {
                    return successCallback(response, null); // JSON 형태가 아닐 때
                }
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .catch(error => errorCallback(error));
}