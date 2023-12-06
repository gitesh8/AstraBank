function profileTab() {
    $(document).ready(function () {
        $("#content").load("profile.html");
    });
}
function homeTab() {
    location.href="/dashboard.html";
    
}
function transactionTab() {
    $(document).ready(function () {
        $("#content").load("transactions.html");
    });
}
function sendmoneyTab() {
    $(document).ready(function () {
        $("#content").load("sendmoney.html");
    });
}

function logout() {
    localStorage.clear();
    location.href="login.html"
}



async function getUserDetails(){

    let jwtToken = localStorage.getItem("jwtToken");

    if(jwtToken==null){
        location.href="/login.html";
    }

    const apiUrl = `${backendUrl}auth/dashboard`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    });

    if(response.ok){
        const responseData = await response.json();
        
        document.getElementById("username").innerText=`Welcome, ${responseData["customer"]["firstName"]}`;
        document.getElementById("accBalance").innerText=`Rs ${responseData["balance"]}`;
    }

}

getUserDetails();