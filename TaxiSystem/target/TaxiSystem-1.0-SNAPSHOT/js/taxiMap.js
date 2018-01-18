/**
 * Created by DNAPC on 02.01.2018.
 */
var map;
var markers = [];
function initMap() {

    var centerLatLng = new google.maps.LatLng(53.9025496, 27.5566752);
    var mapOptions = {
        center: centerLatLng, // Координаты центра мы берем из переменной centerLatLng
        zoom: 12               // Зум по умолчанию. Возможные значения от 0 до 21
    };
    map = new google.maps.Map(document.getElementById("taxiMap"), mapOptions);
}

function setCoord(srcLat,srcLng,dstLat,dstLng) {
    debugger;
    deleteMarkers();
    var srcLatLng = new google.maps.LatLng(srcLat, srcLng);

    var dstLatLng = new google.maps.LatLng(dstLat, dstLng);


    var destinyIcon = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';

    var markerClient = new google.maps.Marker({
        position: srcLatLng,
        map: map,
        title:"I'm here"
    });
    markers.push(markerClient);

    var markerDestiny = new google.maps.Marker({
        position: dstLatLng,
        map: map,
        icon: destinyIcon,
        title:"Drag me!"
    });
    markers.push(markerDestiny);
}

function clearMarkers() {
    setMapOnAll(null);
}

function deleteMarkers() {
    clearMarkers();
    markers = [];
}

function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

google.maps.event.addDomListener(window, "load", initMap);