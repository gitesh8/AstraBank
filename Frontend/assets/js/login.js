async function submitForm() {

    showPreloader();

    const formData = {
        userName: document.getElementById('username').value,
        password: document.getElementById('password').value,
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
