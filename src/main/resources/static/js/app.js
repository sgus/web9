$(function () {
    getListUser();
    updateUser();
    createUser();
});


function getListUser() {
    $('#userListBody').empty();
    $.ajax("/users/list", {
        type: "GET",
        success: function (data) {
            $(data).each(function (i, user) {
                var rolee = "";
                $(user.roles).each(function (i, role) {
                    rolee = rolee + (role.name) + "<br>";
                });

                $('#userListBody')
                    .append("<tr>"
                        + "<td>" + user.id + "</td>"
                        + "<td>" + rolee + "</td>"
                        + "<td>" + user.login + "</td>"
                        + "<td>" + user.password + "</td>"
                        + "<td>" + user.email + "</td>"
                        + "<td class = 'text-center' >"
                        + "<button type='button' id ='editProfile' class='btn btn-info' data-toggle='modal' data-target= '#editModal' data-id= " + user.id + ">Edit </button>"
                        + "<button type='button' id='deleteProfile' class='btn btn-danger' data-id=" + user.id + " >Delete </button>"
                        + "</td>")
            });
            deleteUser();
        }
    });
}

$('#editModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Кнопка, что спровоцировало модальное окно
    var id = button.data('id');
    var user = {};
    var roles = {};

    $.ajax("/users/" + id, {
        type: "GET",
        async: false,
        success: function (data) {
            user = data;
        }
    });
    $.ajax("/users/roles", {
        type: "GET",
        async: false,
        success: function (data) {
            roles = data;
        }
    });

    console.log(roles);

    $('#rolesBox').empty();
    var modal = $(this);
    modal.find('.modal-title').text('Edit user ' + user.login);
    modal.find('.modal-body #id-text').val(user.id);
    modal.find('.modal-body #email-text').val(user.email);
    modal.find('.modal-body #login-text').val(user.login);
    modal.find('.modal-body #password-text').val(user.password);


    var idOfUserRoles = [];
    for (var i = 0; i < user.roles.length; i++) {
        idOfUserRoles.push(user.roles[i].id);
    }
    for (var i = 0; i < roles.length; i++) {
        if (idOfUserRoles.includes(roles[i].id)) {
            $('#rolesBox').append("<input type='checkbox'  checked name ='rols' value=" + roles[i].id + " /> <label>" + roles[i].name + "</label> </br>");
            $.data(($('#rolesBox input')[i]), ("role", {id: roles[i].id, name: roles[i].name}))
        } else {
            $('#rolesBox').append("<input type='checkbox'  name ='rols' value=" + roles[i].id + " /> <label>" + roles[i].name + "</label> </br>")
            $.data(($('#rolesBox input')[i]), ("role", {id: roles[i].id, name: roles[i].name}))
        }
    }
});


function updateUser() {
    $("#modalForm").on("submit", function (e) {
        e.preventDefault();
        var formData = new FormData(this);
        var roles = [];
        for (var i = 0; i < $('#rolesBox input').length; i++) {
            if ($('#rolesBox input')[i].checked) {
                roles.push($.data(($('#rolesBox input')[i])))
            }
        }
        var user = ({
            'id': (parseInt(formData.get("id"))),
            'login': (formData.get("login")),
            'email': (formData.get("email")),
            'password': (formData.get("password")),
            'roles': (roles)
        });


        var d = {
            'user': (user),//{"id":12,"login":"b","email":"b@b.b","password":"b"}
            'roles1': (roles)//[{"id":1,"name":"ROLE_ADMIN"},{"id":2,"name":"ROLE_USER"}]
        };
        $.ajax("/users/update/" + formData.get("id"), {
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(user),
            success: function () {
                $("#editModal").modal('hide');
                getListUser();
            },
            error: function (e) {
                console.log("ERROR : ", e);

            }
        });
    })
}

function createUser() {
    $("#createUserForm").on("submit", function (e) {
        e.preventDefault();
        var formData = new FormData(this);
        var roles = [];
        for (var i = 0; i < $('#rolesBoxCreateUser input').length; i++) {
            if ($('#rolesBoxCreateUser input')[i].checked) {
                let id = ($('#rolesBoxCreateUser input')[i]).dataset.id;
                let name = ($('#rolesBoxCreateUser input')[i]).dataset.name;

                roles.push($.parseJSON('{ "id": ' + id + ',"name": "' + (name) + '"}'))
            }
        }
        console.log(roles)
        var user = ({

            'login': (formData.get("login")),
            'email': (formData.get("email")),
            'password': (formData.get("password")),
            'roles': roles
        });
        console.log(user);

        $.ajax("/users/create/", {
            type: "post",
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(user),
            cache: false,
            processData: false,
            success: function () {
                getListUser();
                $('#pills-home-tab')[0].click();

            },
            error: function (e) {

                console.log("ERROR : ", e);
            }
        });
    })
}


function deleteUser() {
    $('button#deleteProfile').on("click", function (e) {
        e.preventDefault();


        $.ajax("/users/delete/" + $(this).attr('data-id'), {
            type: "post",
            contentType: 'application/json; charset=utf-8',
            dataType: 'html',
            cache: false,
            processData: false,
            success: function () {
                getListUser();
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    })
}