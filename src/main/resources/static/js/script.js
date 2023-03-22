let btnSendPost = document.getElementById("btnSendPost");

btnSendPost.addEventListener("click", (event) => {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'testPostMapping', false);
    xhr.send('{"message": "Привет, пишу тебе с фронтенда"}');

    if (xhr.status != 200) {
        alert(xhr.status + ': ' + xhr.statusText);
    } else {
        alert(xhr.responseText);
    }
});