function search() {
    var s = $('#search').val();
//    var m = $('#w').val() * $('#q').val();
    var r = $('#r').val();
    var t = $('#t').val();
    var l = $('#l').val();
//    if(m == 3)
//        op = 1;
//    else if (m == 4)
//        op = 2;
//    else if (m == 6)
//        op = 3;
//    else if (m == 8)
//        op = 4;
    
    $.post('Buscar', {
//        op : op,
        l : l,
        r : r,
        t : t,
        search : s
    }, function(responseText) {
        $('#results').html(responseText);
    });
}

$(document).ready(function() {
    var i = $("#contenido").attr('idDoc');
    $.post('Archivo', {
        id : i
    }, function(responseText) {
        $('#contenido').html(responseText);
    });
});