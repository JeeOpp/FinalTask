function initMap() {
    var coord = document.getElementById('srcCoord').getAttribute('value');
    var srcArr = coord.split(',');

    var centerLatLng = new google.maps.LatLng(srcArr[0], srcArr[1]);
    var mapOptions = {
        center: centerLatLng, // Координаты центра мы берем из переменной centerLatLng
        zoom: 12               // Зум по умолчанию. Возможные значения от 0 до 21
    };
    var map = new google.maps.Map(document.getElementById("clientMap"), mapOptions);
    var destinyIcon = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';

    var markerClient = new google.maps.Marker({
            position: centerLatLng,
            map: map,
            draggable:true,
            title:"I'm here"
    });

    var markerDestiny = new google.maps.Marker({
        position: centerLatLng,
        map: map,
        icon: destinyIcon,
        draggable:true,
        title:"Drag me!"
    });

    var dragSrcHandler=function(e){
        document.getElementById('srcCoord').setAttribute('value',e.latLng.lat().toFixed(5) + "," + e.latLng.lng().toFixed(5));
        writePrice();
    };

    var dragDestHandler=function(e){
        document.getElementById('dstCoord').setAttribute('value',e.latLng.lat().toFixed(5) + "," + e.latLng.lng().toFixed(5));
        writePrice();
    };

    var writePrice = function () {
        var src = document.getElementById('srcCoord').getAttribute('value');
        var dst = document.getElementById('dstCoord').getAttribute('value');
        var price = calculatePrice(src,dst).toFixed(2);
        document.getElementById('price').setAttribute('value', price);
        document.getElementById('priceView').innerHTML = price+' p.';
    };
    var calculatePrice = function(src,dst) {
        var coefficient = 60;
        var srcArr = src.split(',');
        var dstArr = dst.split(',');

        var sourceLat = srcArr[0];
        var sourceLng = srcArr[1];
        var destinyLat = dstArr[0];
        var destinyLng = dstArr[1];
        var distance = Math.sqrt(Math.pow(destinyLat-sourceLat,2) + Math.pow(destinyLng-sourceLng,2));
        return distance*coefficient;
    };

    markerDestiny.addListener('drag',dragDestHandler);
    markerDestiny.addListener('dragend',dragDestHandler);

    markerClient.addListener('drag',dragSrcHandler);
    markerClient.addListener('dragend',dragSrcHandler);
}
google.maps.event.addDomListener(window, "load", initMap);

