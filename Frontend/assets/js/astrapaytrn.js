// Get the URLSearchParams object
const urlParams = new URLSearchParams(window.location.search);

// Get individual parameter values
const pay = urlParams.get('pay');

if (!pay) {
  // Redirect to another page
  window.location.href = '../astrapay/';
}
async function getTrnDetails(){

    showPreloader();

    const lastDigits = document.getElementById("lastDigits");

    const apiUrl = `${backendUrl}astrapay/card?trnId=${pay}`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    const data = await response.json();
    if (response.ok && data["status"]=="Pending") {
        lastDigits.innerHTML=`${data["accountNumberLast4digits"]}`;
    }
    else{
        location.href="../dashboard/"
    }
    hidePreloader();


}

async function processTransaction(){

    showPreloader();
    const pin1 = document.getElementById("pin1").value;
    const pin2 = document.getElementById("pin2").value;
    const pin3 = document.getElementById("pin3").value;
    const pin4 = document.getElementById("pin4").value;

    const finalPin = pin1+pin2+pin3+pin4;

    const requestData={
        transactionId:pay,
        pin:finalPin
    }

    const apiUrl = `${backendUrl}astrapay/card/verify`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(requestData)
    });

    const data = await response.json();
    if (data["status"]==true) {
        swal({
            title: `Transaction Successfull`,
            icon: "success",
          }).then((result) => {
            if (result) {
              location.href="../dashboard/"
            }
          });
    }
    else if(response.status===403){
        swal({
            title: `Transaction Failed`,
            text:`${data["message"]}`,
            icon: "error",
          }).then((result) => {
            if (result) {
              location.href="../dashboard/"
            }
          });
    }
    else{
        swal({
            title: `Something Went Wrong`,
            text:`${data["message"]}`,
            icon: "error",
          }).then((result) => {
            if (result) {
              location.href="../dashboard/"
            }
          });
       
    }
    hidePreloader();
    
}
getTrnDetails()