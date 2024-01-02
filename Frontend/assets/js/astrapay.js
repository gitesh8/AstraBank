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
async function autoFillCardDetailsFromAccount(){

    showPreloader();

    let jwtToken = localStorage.getItem("jwtToken");

    if (jwtToken == null) {
        hidePreloader()
        swal({
            title: `Login to used this feature`,
            icon: "warning",
        })
        return;
    }

    const apiUrl = `${backendUrl}auth/card`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    });

    if (response.ok) {
        const data = await response.json();
        if (data["message"] == "Yes") {

            const apiUrl = `${backendUrl}auth/card/view-card-or-new`;

            // Simulate a POST request to the backend API
            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwtToken}`
                }
            });
        
            if (response.ok) {
                const data = await response.json();
        
                document.getElementById('cardNumber').value = data.cardNumber;
                document.getElementById('expiry').value = data.expiry;
                document.getElementById('cvv').value = data.cvv;

                // disabling input 
                document.getElementById('cardNumber').disabled=true;
                document.getElementById('expiry').disabled=true;
                document.getElementById('cvv').disabled=true;



                hidePreloader();
            }
        }
        else{

            hidePreloader()

            swal({
                title: `You do not have any card`,
                icon: "warning",
            })
        }
    }
}