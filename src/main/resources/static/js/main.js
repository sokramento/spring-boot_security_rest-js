/**
 *
 */

$('document').ready(function () {

    $('.table .btn').on('click', function (event) {

        event.preventDefault();

           var href = $(this.relatedTarget).attr('href');

        $.get(href, function (user) {
            $('.editForm #idE').val(user.id);
            $('.editForm #usernameE').val(user.username);
            $('.editForm #surnameE').val(user.surname);
            $('.editForm #passwordE').val(user.password);
        });

        $('.editForm #editModal').modal('show');

    });
});