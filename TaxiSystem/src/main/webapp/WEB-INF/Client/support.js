/**
 * Created by DNAPC on 12.12.2017.
 */


function setTaxi(id)
{
    document.querySelector('input[name="taxiId"]').value = id;
}

function setOrder(id, price)
{
    document.getElementById('payText').setAttribute('value',price);
    document.querySelector('input[name="orderId"]').value = id;
}
