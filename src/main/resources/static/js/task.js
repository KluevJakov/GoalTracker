let createTaskBtn = document.getElementById("createTaskBtn");
let editTaskBtn = document.getElementById("editTaskBtn");
let errorBlockTask = document.getElementById("errorBlockTask");
let errorBlockTask1 = document.getElementById("errorBlockTask1");
let taskExec = document.getElementById("taskExec");
errorBlockTask.style.display = "none";
errorBlockTask1.style.display = "none";

document.getElementById('btnCreateTaskModal').addEventListener('click', e => {
    document.getElementById("taskName").value = "";
    document.getElementById("taskDescr").value = "";
    document.getElementById("deadlineInputTask").value = "";

    taskExec.innerHTML = "";
/*
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/team/getTeamMembers?id='+indexTeam, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        alert("Ошибка " + xhr.status);
    } else {
        let members = JSON.parse(xhr.responseText);
        members.forEach(e => {
            taskExec.innerHTML += "<option value='"+e.id+"'>"+e.login+"</option>";
        });
    }
    */
});

createTaskBtn.addEventListener('click', e => {
    let taskName = document.getElementById("taskName");
    let taskDescr = document.getElementById("taskDescr");
    let deadlineInputTask = document.getElementById("deadlineInputTask");
    let executor = document.getElementById("taskExec");

    let task = {
        id : indexGoal,
        name : taskName.value,
        description : taskDescr.value,
        deadline : deadlineInputTask.value
        //executor : executor.value
    };

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/task/create', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(task));

    if (xhr.status != 200) {
        errorBlockTask.style.display = "block";
        errorBlockTask.innerHTML = 'Ошибка: ' + xhr.response;
    } else {
        document.getElementById("closeTaskBtn").click();
        getTasks(indexGoal);
    }
});

document.getElementById('btnEditTaskModal').addEventListener('click', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/task/getTaskById?id='+indexTask, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        alert("Ошибка " + xhr.status);
    } else {
        let responseTask = JSON.parse(xhr.responseText);
        document.getElementById("taskName1").value = responseTask.name;
        document.getElementById("taskDescr1").value = responseTask.description;
        document.getElementById("deadlineInputTask1").value = responseTask.deadline.substring(0, 10);
    }
});

document.getElementById("btnTaskComplete").addEventListener('click', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/task/complete?id='+indexTask, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();

    if (xhr.status != 200) {
        console.log();
    } else {
        getGoals(indexTeam);
        getTasks(indexGoal);
    }
});

editTaskBtn.addEventListener('click', e => {
    let taskName1 = document.getElementById("taskName1");
    let taskDescr1 = document.getElementById("taskDescr1");
    let deadlineInputTask1 = document.getElementById("deadlineInputTask1");

    let task = {
        id : indexTask,
        name : taskName1.value,
        description : taskDescr1.value,
        deadline : deadlineInputTask1.value
    };

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', '/task/update', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(task));

    if (xhr.status != 200) {
        errorBlockTask1.style.display = "block";
        errorBlockTask1.innerHTML = 'Ошибка: ' + xhr.response;
    } else {
        document.getElementById("closeTaskBtn1").click();
        getTasks(indexGoal);
    }
});

document.getElementById('btnRemoveTask').addEventListener('click', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('DELETE', '/task/delete?id='+indexTask, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        console.log("Ошибка " + xhr.status);
    } else {
        indexTask = -1;
        clearSelection("task");
        getTasks(indexGoal);
        document.getElementById('btnRemoveTask').disabled = true;
        document.getElementById('btnEditTaskModal').disabled = true;
    }
});