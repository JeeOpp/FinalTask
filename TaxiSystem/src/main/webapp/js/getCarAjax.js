/**
 * Created by DNAPC on 23.12.2017.
 */
var numberAction;
var req = new XMLHttpRequest();
function getCarList(action) {
    var method = action;
    if(action == 'change'){
        numberAction = 0;
    }else numberAction = 1;
    req.onreadystatechange = handlerFunction;
    req.open("POST", "Controller", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send("method=dispatcher&action=getCarList");
}

function handlerFunction() {
    var number, name, colour;
    if(req.readyState==4 && req.status==200){
        var objList = JSON.parse(req.responseText);
        var tags = document.getElementsByClassName('cars');
        for(var i=0; i < tags.length;i++){
            tags[i].innerHTML='';
        }
        var tag = tags[numberAction];
        for (var jsonObjId in objList) {
            for (var prop in (jsonObj = objList[jsonObjId])) {
                if(prop == 'number'){
                    number = jsonObj[prop];
                }
                if(prop == 'name'){
                    name = jsonObj[prop];
                }
                if(prop == 'colour'){
                    colour = jsonObj[prop];
                }
            }
        tag.innerHTML+='<input type="radio" name="carNumber" value='+number+' checked/>'+number+' '+name+' '+colour+'</br>';
        }
    }
}

function check()
{
    var method;
    if(numberAction == 0){
        method = 'changeCheckedCar';
    }else method = 'regCheckedCar';
    var inp = document.getElementsByName('carNumber');
    for (var i = 0; i < inp.length; i++) {
        if (inp[i].type == "radio" && inp[i].checked) {
            document.getElementById(method).setAttribute('value',inp[i].value);
        }
    }
}

function changeTaxiId(taxiId) {
    document.getElementById('changeTaxiId').setAttribute('value',taxiId);
}