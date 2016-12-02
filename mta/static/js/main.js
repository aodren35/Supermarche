//test


$(document).ready(function() {
    $("#create-clients-form button").click( function() {
        var new_user = new Object();

        
        new_user.nombre_de_client = $('input#nombre_de_client').val()

        console.log(new_user);

        $.ajax({
            type: "post",
            url: "/supermarche/clients/",
            data: JSON.stringify(new_user),
            success: function(data){
                console.log(data);
                window.location = "/supermarche";
            },
            dataType: "json",
            contentType : "application/json"
        });
    });
     if($('#clients-table').length) {
        var users_table = $('#clients-table tbody');

        $.ajax({
            type: "get",
            url: "/supermarche/clients/",
            success: function(data){
                console.log(data);

                $.each(data, function (item) {
                    var id = data[item].id;
                    var url = data[item].url;

                    users_table.append(
                    '<tr>' +
                        '<th><a href="' + url + '">' + id + '</a></th>' +
                    '</tr>'
                    );

                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ajax error");
            },
            dataType: "json",
            contentType : "application/json"
        });
    }
});