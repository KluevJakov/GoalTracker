sendPost();
function sendPost() {
    let quit = document.getElementById("quit");
    let login = document.getElementById("login");
    let signup = document.getElementById("signup");
    let lk = document.getElementById("lk");
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/checkAuth', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        quit.style.display = "none";
    } else {
        if (xhr.responseText === "true") {
            quit.style.display = "block";
            lk.style.display = "block";
            login.style.display = "none";
            signup.style.display = "none";
        } else {
            quit.style.display = "none";
            lk.style.display = "none";
            login.style.display = "block";
            signup.style.display = "block";
        }
    }
}

