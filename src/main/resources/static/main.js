let tableBody = document.querySelector('#tableBody');
const url = 'http://localhost:8080/api/users';
let output = '';
const newUserForm = document.querySelector('#newUserForm');
const editUserForm = document.querySelector('#editUserForm');
const deleteUserForm = document.querySelector('#deleteUserForm');
const usersTableActive = document.querySelector('#usersTableActive');
const adminInfo = document.querySelector('#adminInfo');

// GET users, METHOD: GET
function getUsers() {
    fetch(url).then((response) => {
        response.json()
            .then((users) => {
                users.forEach((user) => {
                    addUsersToTable(user)
                });
            });
    });
}

const addUsersToTable = (user) => {
    output +=
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.username + '</td>' +
        '<td>' + user.surname + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + user.roles.map(roleUser => roleUser.rolename) + '</td>' +
        '<td>' +
        '<button onclick="editUserById(' + user.id + ')" class="btn btn-info edit-btn" ' +
        'data-toggle="modal" data-target="#edit"' +
        '>Edit</button></td>' +
        '<td>' +
        '<button onclick="deleteUserById(' + user.id + ')" class="btn btn-danger delete-btn" ' +
        'data-toggle="modal" data-target="#delete"' +
        '>Delete</button></td>' +
        '</tr>'
    tableBody.innerHTML = output;
}

getUsers();

function clickToPanel() {
    usersTableActive.addEventListener('click', (event) => {
        event.preventDefault();
        output = '';
    })
}

function newUser() {
    newUserForm.addEventListener('submit', (event) => {
        event.preventDefault();
        let username = document.getElementById('usernameNew').value;
        let surname = document.getElementById('surnameNew').value;
        let age = document.getElementById('ageNew').value;
        let email = document.getElementById('emailNew').value;
        let password = document.getElementById('passwordNew').value;
        let roles = getRoles(Array.from(document.getElementById('rolesNew').selectedOptions)
            .map(role => role.value));

        fetch(url, {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify({
                username: username,
                surname: surname,
                age: age,
                email: email,
                password: password,
                roles: roles
            })
        })
            .then(() => {
                newUserForm.reset();
            })
            .then(() => {
                output = '';
            })
    });
}


function editUserById(id) {
    fetch('http://localhost:8080/api/users/' + id, {
        method: 'GET',
        dataType: 'json',
    })
        .then(res => {
            res.json().then(user => {
                $('#idEdit').val(user.id)
                $('#usernameEdit').val(user.username)
                $('#surnameEdit').val(user.surname)
                $('#ageEdit').val(user.age)
                $('#emailEdit').val(user.email)
            })
        })
}

function editUser() {
    let id = document.getElementById('idEdit').value;
    let username = document.getElementById('usernameEdit').value;
    let surname = document.getElementById('surnameEdit').value;
    let age = document.getElementById('ageEdit').value;
    let email = document.getElementById('emailEdit').value;
    let password = document.getElementById('passwordEdit').value;
    let roles = getRoles(Array.from(document.getElementById('rolesEdit').selectedOptions)
        .map(role => role.value));

    fetch('http://localhost:8080/api/users/' + id, {
        method: "PATCH",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            username: username,
            surname: surname,
            age: age,
            email: email,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            editUserForm.reset();
        })
        .then(() => {
            output = '';
            getUsers();
            closeForm();
        })
}


function deleteUserById(id) {
    fetch('http://localhost:8080/api/users/' + id, {
        method: 'GET',
        dataType: 'json',
    })
        .then(res => {
            res.json().then(user => {
                $('#idDelete').val(user.id)
                $('#usernameDelete').val(user.username)
                $('#surnameDelete').val(user.surname)
                $('#ageDelete').val(user.age)
                $('#emailDelete').val(user.email)
                user.roles.map(role => {
                    $('#rolesDelete').append('<option id="' + role.id + '" name = "' + role.rolename + '">' +
                        role.rolename + '</option>')
                })
            })
        })
}

function deleteUser() {
    let id = document.getElementById('idDelete').value;
    fetch(
        'http://localhost:8080/api/users/' + id,
        {
            method: 'DELETE',
            dataType: 'json'
        })
        .then(() => {
            deleteUserForm.reset();
        })
        .then(() => {
            output = '';
            getUsers();
            closeForm();
        })
}

function getRoles(list) {
    let roles = [];
    if (list.indexOf("ROLE_USER") >= 0) {
        roles.push({"id": 1});
    }
    if (list.indexOf("ROLE_ADMIN") >= 0) {
        roles.push({"id": 2});
    }
    return roles;
}

function closeForm() {
    $("#edit .close").click();
    document.getElementById("editUserForm").reset();
    $("#delete .close").click();
    document.getElementById("deleteUserForm").reset();
    $('#rolesDelete > option').remove();
}