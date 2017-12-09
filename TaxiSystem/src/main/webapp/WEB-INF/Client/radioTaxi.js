/**
 * Created by DNAPC on 07.12.2017.
 */
function check()
{
    var inp = document.getElementsByName('taxi');
    for (var i = 0; i < inp.length; i++) {
        if (inp[i].type == "radio" && inp[i].checked) {
            document.querySelector('input[name="checkedTaxiId"]').value = inp[i].value;
        }
    }
}
