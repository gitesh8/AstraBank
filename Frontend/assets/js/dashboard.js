var mediaQuery = window.matchMedia("(max-width: 914px)");
function profileTab() {
    if (mediaQuery.matches) {
        document.getElementById("sidebar").style.display = "none";
        handleMenu("Close")
    }

    $(document).ready(function () {
        $("#content").load("profile.html");
    });
}
function homeTab() {
    location.href = "/dashboard";

}
function transactionTab() {
   
    if (mediaQuery.matches) {
        document.getElementById("sidebar").style.display = "none";
        handleMenu("Close")
    }
    $(document).ready(function () {
        $("#content").load("transactions.html");
    });
}
function sendmoneyTab() {
   
    if (mediaQuery.matches) {
        document.getElementById("sidebar").style.display = "none";
        handleMenu("Close")
    }
    $(document).ready(function () {
        $("#content").load("sendmoney.html");
    });
}
function cardTab() {
    
    if (mediaQuery.matches) {
        document.getElementById("sidebar").style.display = "none";
        handleMenu("Close")
    }
    $(document).ready(function () {
        $("#content").load("card.html");
    });
}

function astrapay() {
    if (mediaQuery.matches) {
        document.getElementById("sidebar").style.display = "none";
        handleMenu("Close")
    }
  window.open("../astrapay/");
}

function logout() {
    localStorage.clear();
    location.href = "../login.html"
}

function handleMenu(option) {
    if (option == "Open") {
        document.getElementById("sidebar").style.display = "block";
        document.getElementById("menuOpen").style.display = "none";
        document.getElementById("closeMenu").style.display = "block";
    }
    else {
        document.getElementById("sidebar").style.display = "none";
        document.getElementById("closeMenu").style.display = "none";

        document.getElementById("menuOpen").style.display = "block";

    }
}


async function getUserDetails() {

    let jwtToken = localStorage.getItem("jwtToken");

    if (jwtToken == null) {
        location.href = "/login.html";
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

    if (response.ok) {
        const responseData = await response.json();

        document.getElementById("username").innerText = `Welcome, ${responseData["customer"]["firstName"]}`;
        document.getElementById("accBalance").innerText = `Rs ${responseData["balance"]}`;
    }

}

getUserDetails();