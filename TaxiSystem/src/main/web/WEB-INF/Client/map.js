function initMap() {
    var centerLatLng = new google.maps.LatLng(53.9045398, 27.5615244);
    var mapOptions = {
        center: centerLatLng, // Координаты центра мы берем из переменной centerLatLng
        zoom: 12               // Зум по умолчанию. Возможные значения от 0 до 21
    };
    var map = new google.maps.Map(document.getElementById("map"), mapOptions);

    var marker = new google.maps.Marker({
        position: centerLatLng,
        map: map,
        draggable:true,
        title:"Drag me!"
    });

    dragHandler=function(e){
        document.querySelector('input[name="position"]').value = e.latLng.lat() + "," + e.latLng.lng();
    };
    marker.addListener('drag',dragHandler);
    marker.addListener('dragend',dragHandler);
}
google.maps.event.addDomListener(window, "load", initMap);