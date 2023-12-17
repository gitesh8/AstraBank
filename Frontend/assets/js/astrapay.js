async function processPayment() {

    showPreloader();

    const cardNumber = document.getElementById("cardNumber").value;
    const expiry = document.getElementById("expiry").value;
    const cvv = document.getElementById("cvv").value;
    const amount = document.getElementById("amount").value;

    if(!validateInput(cardNumber) || !validateInput(expiry) || !validateInput(cvv) || !validateInput(amount)){
        swal({
            title: `All Fields are Required`,
            icon: "warning",
          })
          hidePreloader();
        return;
    }

    const requestData={
        cardNumber:cardNumber,
        expiry:expiry,
        cvv:cvv,
        amount:amount
    }

    const apiUrl = `${backendUrl}astrapay/card`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(requestData)
    });

    const data = await response.json();
    if (response.ok && data["status"]=="Pending") {
        location.href=`verifytrn.html?pay=${data.transactionId}`;
    }
    else if(response.status==403){
        swal({
            title: `${data["message"]}`,
            icon: "error",
          })
    }
    hidePreloader();
}
function validateInput(inputValue) {
    // Check for null or undefined
    if (inputValue === null || inputValue === undefined) {
        return false;
    }

    // Check for empty string
    if (inputValue.trim() === '') {
        return false;
    }

    // Check for a string consisting of only whitespace characters
    if (/^\s*$/.test(inputValue)) {
        return false;
    }

    // If none of the above conditions are met, the input is considered valid
    return true;
}