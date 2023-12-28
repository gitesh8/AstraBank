
async function getUserDetails(){

    let jwtToken = localStorage.getItem("jwtToken");

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
        const data = await response.json();

        // Account details
        document.getElementById("accountNumber").value=data["accountNumber"];
        document.getElementById("acctype").value=data["accountType"];
        document.getElementById("accstatus").value=data["status"];


        // profile details
        document.getElementById("firstname").value=data["customer"]["firstName"];
        document.getElementById("lastname").value=data["customer"]["lastName"];
        document.getElementById("dob").value=data["customer"]["dob"];

        // checking if the email exists
        if(data["customer"]["email"]!=null){
            document.getElementById("email").value=data["customer"]["email"];
        }
        else{
            document.getElementById("email").value="Not Available";
        }
       
    }

}

getUserDetails();