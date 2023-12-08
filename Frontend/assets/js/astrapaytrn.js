// Get the URLSearchParams object
const urlParams = new URLSearchParams(window.location.search);

// Get individual parameter values
const pay = urlParams.get('pay');
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
    hidePreloader();


}
getTrnDetails()