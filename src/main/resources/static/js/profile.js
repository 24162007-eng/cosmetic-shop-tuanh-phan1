$(document).ready(function () {
    var token = localStorage.getItem('token');

    if (!token) {
        window.location.href = '/login';
        return;
    }

    function goLogin() {
        localStorage.removeItem('token');
        window.location.href = '/login';
    }

    $.ajax({
        type: 'GET',
        url: '/users/me',
        dataType: 'json',
        headers: { 'Authorization': 'Bearer ' + token },
        success: function (data) {
            $('#fullName').text(data.fullName);
            $('#email').text(data.email);
            $('#images').attr('src', data.images);
        },
        error: goLogin
    });

    $.ajax({
        type: 'GET',
        url: '/products',
        dataType: 'json',
        headers: { 'Authorization': 'Bearer ' + token },
        success: function (products) {
            var body = $('#productBody');
            body.empty();
            products.forEach(function (p) {
                var row = $('<tr>');
                row.append($('<td>').text(p.id));
                row.append($('<td>').text(p.name));
                row.append($('<td>').text(p.brand));
                row.append($('<td>').text(p.category));
                row.append($('<td>').text(Number(p.price).toLocaleString('vi-VN') + ' đ'));
                row.append($('<td>').text(p.stock));
                body.append(row);
            });
        },
        error: goLogin
    });

    $('#logout').click(function () {
        localStorage.clear();
        window.location.href = '/login';
    });
});