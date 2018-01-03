/**
 * Created by DNAPC on 23.12.2017.
 */

var req = new XMLHttpRequest();
function getCarList() {

    req.onreadystatechange = handlerFunction;
    req.open("POST", "Controller", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send("method=dispatcher&action=getCarList");
}

function handlerFunction() {
    var number, name, colour;
    if(req.readyState==4 && req.status==200){
        var objList = JSON.parse(req.responseText);
        var tag = document.getElementById('cars');
        tag.innerHTML='';
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
    var inp = document.getElementsByName('carNumber');
    for (var i = 0; i < inp.length; i++) {
        if (inp[i].type == "radio" && inp[i].checked) {
            document.querySelector('input[name="checkedCarNumber"]').value = inp[i].value;
        }
    }
}