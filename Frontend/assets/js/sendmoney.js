async function sendMoney(){

    showPreloader();
    let jwtToken = localStorage.getItem("jwtToken");

    const formData = {
        accountNumber: document.getElementById('accNumber').value,
        amount: document.getElementById('accAmount').value,
        remark: document.getElementById('remark').value
    };

    // TODO: Replace the following placeholder URL with your actual backend API endpoint
    const apiUrl = `${backendUrl}auth/send-money`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        },
        body: JSON.stringify(formData),
    });

    const data = await response.json();
    hidePreloader();
    if (data["status"]==true) {
        swal({
            title: `${data["message"]}`,
            icon: "success",
          });
    }else{
        swal({
            title: `${data["message"]}`,
            icon: "error",
          });
    }

   
}