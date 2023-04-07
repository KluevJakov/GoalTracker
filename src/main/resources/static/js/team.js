let indexGoal = -1;
let indexTask = -1;
let indexTeam = -1;

getTeams();

let createTeamBtn = document.getElementById("createTeamBtn");
let editTeamBtn = document.getElementById("editTeamBtn");
let errorBlockTeamCreate = document.getElementById("errorBlockTeamCreate");
let errorBlockTeamEdit = document.getElementById("errorBlockTeamEdit");
let memberSearchCreate = document.getElementById("memberSearchCreate");
let createSearchUl = document.getElementById("createSearchUl");
let createChooseUl = document.getElementById("createChooseUl");
errorBlockTeamCreate.style.display = "none";
errorBlockTeamEdit.style.display = "none";

memberSearchCreate.addEventListener('keyup', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/getByLogin?login='+e.target.value, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();

    if (xhr.status != 200) {
        console.log("error during search");
    } else {
        createSearchUl.innerHTML = "";
        JSON.parse(xhr.responseText).forEach(e => {
            let indexes = [];
            Array.from(document.getElementsByClassName('liChoose')).forEach(el => {
                indexes.push(el.value);
            });
            if (!indexes.includes(e.id) && indexes.length <= 5) {
                createSearchUl.innerHTML += "<li value='"+e.id+"' class='list-group-item liPos liUser'>"+e.login+"</li>";
            }
        });
         Array.from(document.getElementsByClassName('liUser')).forEach(el => {
            el.addEventListener('click', e => {
                createChooseUl.innerHTML += "<li value='"+e.target.value+"' onclick='removeMe(event)' class='list-group-item liPos liChoose'>"+e.target.innerText+"</li>";
                e.target.remove();
            });
        });
    }
});
memberSearchEdit.addEventListener('keyup', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/getByLogin?login='+e.target.value, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();

    if (xhr.status != 200) {
        console.log("error during search");
    } else {
        editSearchUl.innerHTML = "";
        JSON.parse(xhr.responseText).forEach(e => {
            let indexes = [];
            Array.from(document.getElementsByClassName('liChoose')).forEach(el => {
                indexes.push(el.value);
            });
            if (!indexes.includes(e.id) && indexes.length <= 5) {
                editSearchUl.innerHTML += "<li value='"+e.id+"' class='list-group-item liPos liUser'>"+e.login+"</li>";
            }
        });
         Array.from(document.getElementsByClassName('liUser')).forEach(el => {
            el.addEventListener('click', e => {
                editChooseUl.innerHTML += "<li value='"+e.target.value+"' onclick='removeMe(event)' class='list-group-item liPos liChoose'>"+e.target.innerText+"</li>";
                e.target.remove();
            });
        });
    }
});

createTeamBtn.addEventListener('click', e => {
    let members = [];
    Array.from(document.getElementsByClassName('liChoose')).forEach(el => {
        members.push({ id : el.value });
    });

    let team = {
        id : null,
        title : document.getElementById('teamName').value,
        members : members
    };

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/team/create', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(team));

    if (xhr.status != 200) {
        console.log(xhr.error);
    } else {
        console.log(xhr.responseText);
        document.getElementById("closeTeamBtnCreate").click();
        getTeams();
    }
});

document.getElementById('btnEditTeamModal').addEventListener('click', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/team/getTeamById?id='+indexTeam, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        console.log("Ошибка " + xhr.status);
    } else {
        let responseGoal = JSON.parse(xhr.responseText);
        document.getElementById("teamNameEdit").value = responseGoal.title;

        editChooseUl.innerHTML = "";
        responseGoal.members.forEach(e => {
            editChooseUl.innerHTML += "<li value='"+e.id+"' onclick='removeMe(event)' class='list-group-item liPos liChoose'>"+e.login+"</li>";
        });
    }
});

editTeamBtn.addEventListener('click', e => {
    let members = [];
    Array.from(document.getElementsByClassName('liChoose')).forEach(el => {
        members.push({ id : el.value });
    });

    let team = {
        id : indexTeam,
        title : document.getElementById("teamNameEdit").value,
        members : members
    };

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', '/team/update', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(team));

    if (xhr.status != 200) {
        errorBlock.style.display = "block";
        errorBlock.innerHTML = 'Ошибка: ' + xhr.response;
    } else {
        document.getElementById("closeTeamBtnEdit").click();
        getTeams();
    }
});

document.getElementById('btnRemoveTeam').addEventListener('click', e => {
    let xhr = new XMLHttpRequest();
    xhr.open('DELETE', '/team/delete?id='+indexTeam, false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        console.log("Ошибка " + xhr.status);
    } else {
        indexTeam = -1;
        clearSelection("all");
        getTeams();
        goals.innerHTML = "";
        tasks.innerHTML = "";
        document.getElementById("btnRemoveTeam").disabled = true;
        document.getElementById('btnCreateGoalModal').disabled = true;
    }
});

closeTeamBtnEdit.addEventListener('click', e => {
    createSearchUl.innerHTML = "";
    createChooseUl.innerHTML = "";
    editSearchUl.innerHTML = "";
    editChooseUl.innerHTML = "";
});

closeTeamBtnCreate.addEventListener('click', e => {
    createSearchUl.innerHTML = "";
    createChooseUl.innerHTML = "";
    editSearchUl.innerHTML = "";
    editChooseUl.innerHTML = "";
});

function getTeams() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/team/getTeamByInitiatorId', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    if (xhr.status != 200) {
        alert("Ошибка " + xhr.status);
    } else {
        let teams = document.getElementById("teams");
        let response = JSON.parse(xhr.responseText);
        teams.innerHTML = "";

        for (let i = 0; i < response.length; i++) {
            teams.innerHTML += "<li value='"+response[i].id+"' class='list-group-item liPos liTeam'>"+response[i].title+"</li>";
        }

        Array.from(document.getElementsByClassName('liTeam')).forEach(el => {
            el.addEventListener('click', e => {
                    clearSelection("team");
                    indexTeam = e.target.value;
                    document.getElementById('btnEditTeamModal').disabled = false;
                    document.getElementById('btnCreateGoalModal').disabled = false;
                    document.getElementById("btnRemoveTeam").disabled = false;
                    document.getElementById('btnEditGoalModal').disabled = true;
                    document.getElementById('btnRemoveGoal').disabled = true;
                    document.getElementById('btnCreateTaskModal').disabled = true;
                    document.getElementById('btnEditTaskModal').disabled = true;
                    document.getElementById('btnRemoveTask').disabled = true;
                    e.target.classList.add("selectedRow");
                    getGoals(indexTeam);
                    tasks.innerHTML = "";
            });
        });
    }
   document.getElementById('btnEditTeamModal').disabled = true;
   document.getElementById('btnEditGoalModal').disabled = true;
   document.getElementById('btnRemoveGoal').disabled = true;
   document.getElementById('btnCreateTaskModal').disabled = true;
   document.getElementById('btnEditTaskModal').disabled = true;
   document.getElementById('btnRemoveTask').disabled = true;
}

function removeMe(event) {
    event.target.remove();
}