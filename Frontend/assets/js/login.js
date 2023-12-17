// Get the URLSearchParams object
const urlParams = new URLSearchParams(window.location.search);

// Get individual parameter values
const serverStatus = urlParams.get('server-status');
if(serverStatus!="started"){
    location.href = "starting-server.html?redirect=login.html";
}

// checking if the user already logged in
let jwtToken = localStorage.getItem("jwtToken");

if (jwtToken != null) {
    location.href = "/dashboard/";
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
async function submitForm() {



    const userName = document.getElementById('username').value
    const password = document.getElementById('password').value

    if (!validateInput(userName)) {
        swal({
            title: `Username is Required`,
            icon: "error",
        });
        return;
    }

    if (!validateInput(password)) {
        swal({
            title: `Password is Required`,
            icon: "error",
        });
        return;
    }
    showPreloader();
    const formData = {
        userName: userName,
        password: password,
    };

    // TODO: Replace the following placeholder URL with your actual backend API endpoint
    const apiUrl = `${backendUrl}signin`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    });

    const data = await response.json();

    if (data["status"] == true) {
        location.href = "/dashboard";
        localStorage.setItem("jwtToken", data["jwtToken"]);
    }
    else if (response.status === 401 || data["message"] == "Bad credentials") {
        swal({
            title: `Invalid Credientials`,
            icon: "error",
        });
    }
    else if (response.status === 403) {
        swal({
            title: `${data["message"]}`,
            icon: "error",
        });
    }

    hidePreloader();
}

function redirectUser(url) {
    location.href = url;
}
