async function submitForm() {

    showPreloader();

    const formData = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        accountType: document.getElementById('accountType').value,
        dateOfBirth: document.getElementById('dateOfBirth').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
    };

    // TODO: Replace the following placeholder URL with your actual backend API endpoint
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

    hidePreloader();
}

function redirectUser(url){
    location.href=url;
}
