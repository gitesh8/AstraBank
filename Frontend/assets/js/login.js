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

    if(!validateInput(userName)){
        swal({
            title: `Username is Required`,
            icon: "error",
          });
          return;
    }

    if(!validateInput(password)){
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

    if (data["status"]==true) {
        location.href="/dashboard.html";
        localStorage.setItem("jwtToken",data["jwtToken"]);
    }
    else{
        swal({
            title: `${data["message"]}`,
            icon: "error",
          });
    }

    hidePreloader();
}

function redirectUser(url){
    location.href=url;
}
