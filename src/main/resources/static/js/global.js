
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
                return response.json();
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .then(data => successCallback(data))
        .catch(error => errorCallback(error));
}