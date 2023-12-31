// checking if the user already logged in
let jwtToken = localStorage.getItem("jwtToken");

// regular expression to match only alphanumeric characters
var alphanumericRegex = /^[a-zA-Z0-9]+$/;

if (jwtToken != null) {
    location.href = "/dashboard/";
}

// Get the URLSearchParams object
const urlParams = new URLSearchParams(window.location.search);

// Get individual parameter values
const serverStatus = urlParams.get('server-status');
if(serverStatus!="started"){
    location.href = "starting-server.html?redirect=signup.html";
}
function validateInput(inputValue,type) {
    // Check for null or undefined
    if (inputValue === null || inputValue === undefined) {
        return false;
    }

    // Check for empty string
    if (inputValue.trim() === '') {
        return false;
    }

    // Check for a whitespace characters
    if (/^\s*$/.test(inputValue)) {
        return false;
    }

    if(type=="username" && !alphanumericRegex.test(inputValue.trim())){
        return false;
    }

    // If none of the above conditions are met, the input is considered valid
    return true;
}

async function submitForm() {

    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const accountType = document.getElementById('accountType').value;
    const dateOfBirth = document.getElementById('dateOfBirth').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Validate each input
    if (!validateInput(firstName)) {
        swal({
            title: `Firstname is required`,
            icon: "warning",
          });
        return;
    }

    if (!validateInput(lastName)) {
        swal({
            title: `Lastname is required`,
            icon: "warning",
          });
        return;
    }

    if (!validateInput(accountType)) {
        swal({
            title: `Account Type is required`,
            icon: "warning",
          });
        return;
    }

    if (!validateInput(dateOfBirth)) {
        swal({
            title: `Date of Birth is required`,
            icon: "warning",
          });
        return;
    }

    if (!validateInput(username,"username")) {
        swal({
            title: `Username Should  only contain Character and Number`,
            icon: "warning",
          });
        return;
    }

    if (!validateInput(password)) {
        swal({
            title: `Password is required`,
            icon: "warning",
          });
        return;
    }
    

    

    const formData = {
        firstName: firstName,
        lastName: lastName,
        accountType: accountType,
        dateOfBirth: dateOfBirth,
        username: username,
        password:password
    };



    showPreloader();
    const apiUrl = `${backendUrl}signup`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    });

    const data = await response.json();

    if (data["status"]==true) {
        document.getElementById("registrationForm").style.display="none";
        document.getElementById("accountdetails").style.display="block";

        document.getElementById("heading").innerText="Account Details";


        document.getElementById("bankName").innerText=data["bankName"];
        document.getElementById("usersName").innerText=data["userName"];
        document.getElementById("accNo").innerText=data["accountNumber"];
        document.getElementById("accHolder").innerText=data["accountHolderName"];
        document.getElementById("sucessmsg").innerText=data["message"];
    }
    else{
        swal({
            title: `${data["message"]}`,
            icon: "warning",
          });
    }

    hidePreloader();
}

function redirectUser(url){
    location.href=url;
}
