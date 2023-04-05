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

    console.log(members);
    console.log(team);

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/team/create', false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(team));

    if (xhr.status != 200) {
        console.log(xhr.error);
    } else {
        console.log(xhr.responseText);
    }
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
/*
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
        */
    }
   // document.getElementById('btnEditGoalModal').disabled = true;
}

function removeMe(event) {
    event.target.remove();
}