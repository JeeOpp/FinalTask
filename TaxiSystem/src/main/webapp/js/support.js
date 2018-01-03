/**
 * Created by DNAPC on 12.12.2017.
 */


function setReview(orderId, taxiId)
{
    document.getElementById('orderId').setAttribute('value',orderId);
    document.querySelector('input[name="taxiId"]').value = taxiId;
}

function setOrder(id, price)
{
    document.getElementById('payText').setAttribute('value',price);
    document.querySelector('input[name="orderId"]').value = id;
}
