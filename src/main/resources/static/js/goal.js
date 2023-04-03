let indexGoal = -1;
let indexTask = -1;

getGoals();

let createGoalBtn = document.getElementById("createGoalBtn");
let editGoalBtn = document.getElementById("editGoalBtn");
let errorBlock = document.getElementById("errorBlock");
let errorBlock1 = document.getElementById("errorBlock1");
errorBlock.style.display = "none";
errorBlock1.style.display = "none";

document.getElementById('btnCreateGoalModal').addEventListener('click', e => {
    document.getElementById("goalName").value = "";
    document.getElementById("goalDescr").value = "";
    document.getElementById("deadlineInput").value = "";
});

document.getElementById('btnEditGoalModal').addEventListener('click', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/goal/getGoalById?id='+indexGoal, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        alert("Ошибка " + xhr.status);
    } else {
        let responseGoal = JSON.parse(xhr.responseText);
        document.getElementById("goalName1").value = responseGoal.name;
        document.getElementById("goalDescr1").value = responseGoal.description;
        document.getElementById("deadlineInput1").value = responseGoal.deadline.substring(0, 10);
    }
});

editGoalBtn.addEventListener('click', e => {
    let goalName1 = document.getElementById("goalName1");
    let goalDescr1 = document.getElementById("goalDescr1");
    let deadlineInput1 = document.getElementById("deadlineInput1");

    let goal = {
        id : indexGoal,
        name : goalName1.value,
        description : goalDescr1.value,
        deadline : deadlineInput1.value
    };

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', '/goal/update', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(goal));

    if (xhr.status != 200) {
        errorBlock.style.display = "block";
        errorBlock.innerHTML = 'Ошибка: ' + xhr.response;
    } else {
        document.getElementById("closeGoalBtn1").click();
        getGoals();
    }
});

createGoalBtn.addEventListener('click', e => {
    let goalName = document.getElementById("goalName");
    let goalDescr = document.getElementById("goalDescr");
    let deadlineInput = document.getElementById("deadlineInput");

    let goal = {
        id : null,
        name : goalName.value,
        description : goalDescr.value,
        deadline : deadlineInput.value
    };

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/goal/create', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(goal));

    if (xhr.status != 200) {
        errorBlock.style.display = "block";
        errorBlock.innerHTML = 'Ошибка: ' + xhr.response;
    } else {
        document.getElementById("closeGoalBtn").click();
        getGoals();
    }
});

function getGoals() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/goal/getMyGoals', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        alert("Ошибка " + xhr.status);
    } else {
        let goals = document.getElementById("goals");
        let response = JSON.parse(xhr.responseText);
        goals.innerHTML = "";

        for (let i = 0; i < response.length; i++) {
            goals.innerHTML += "<li value='"+response[i].id+"' class='list-group-item liPos liGoal " + (response[i].success ? "successColor" : "failureColor") + "'>"+
            response[i].name+"</li>";
        }

        Array.from(document.getElementsByClassName('liGoal')).forEach(el => {
            el.addEventListener('click', e => {
                    clearSelection("goal");
                    indexGoal = e.target.value;
                    document.getElementById('btnEditGoalModal').disabled = false;
                    document.getElementById("btnCreateTaskModal").disabled = false;
                    e.target.classList.add("selectedRow");
                    getTasks(indexGoal);
            });
        });
    }
    document.getElementById('btnEditGoalModal').disabled = true;
}

function getTasks(goalId) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/task/getTasksByGoalId?id='+goalId, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        alert("Ошибка " + xhr.status);
    } else {
        let tasks = document.getElementById("tasks");
        let response = JSON.parse(xhr.responseText);
        tasks.innerHTML = "";

        for (let i = 0; i < response.length; i++) {
            tasks.innerHTML += "<li value='"+response[i].id+"' class='list-group-item liPos liTask " + (response[i].success ? "successColor" : "failureColor") + "'>"+
            response[i].name+"</li>";
        }

        Array.from(document.getElementsByClassName('liTask')).forEach(el => {
            el.addEventListener('click', e => {
                    clearSelection("task");
                    indexTask = e.target.value;
                    document.getElementById('btnEditTaskModal').disabled = false;
                    e.target.classList.add("selectedRow");
            });
        });
    }
}

function clearSelection(cat) {
    if (cat == "goal") {
        Array.from(document.getElementsByClassName('liGoal')).forEach(el => {
            el.classList.remove("selectedRow");
        });
        document.getElementById('btnEditTaskModal').disabled = true;
    } else if (cat == "task") {
        Array.from(document.getElementsByClassName('liTask')).forEach(el => {
            el.classList.remove("selectedRow");
        });
    }
}