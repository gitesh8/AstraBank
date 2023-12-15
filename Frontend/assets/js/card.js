async function GenerateCardorViewCard() {

    showPreloader();

    let jwtToken = localStorage.getItem("jwtToken");

    if (jwtToken == null) {
        location.href = "/login.html";
    }

    const apiUrl = `${backendUrl}auth/card/view-card-or-new`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    });

    if (response.ok) {
        const data = await response.json();
        document.getElementById("noCard").style.display = "none";
        document.getElementById("hasCard").style.display = "flex";

        if (data["status"] != "pinNotSet") {
            document.getElementById("setPin").style.display = "none";
        }

        document.getElementById('cardNumber').innerText = data.cardNumber.toString().replace(/(\d{4})/g, "$1 ");
        document.getElementById('cardExpiry').innerText = data.expiry;
        document.getElementById('cardHolder').innerText = data.cardHolderName;
        document.getElementById('cardCVV').innerText = "Cvv: " + data.cvv;

        // setting card status
        if (data.status == "Active") {
            document.getElementById("card-status").style.background = "green";
            document.getElementById("card-status").innerText = data.status;
        }
        else {
            document.getElementById("card-status").style.background = "red";
            document.getElementById("card-status").innerText = "Deactive";
        }

    }
    hidePreloader();
}

async function checkHasCardorNot() {

    showPreloader();

    let jwtToken = localStorage.getItem("jwtToken");

    if (jwtToken == null) {
        location.href = "/login.html";
    }

    const apiUrl = `${backendUrl}auth/card`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    });

    if (response.ok) {
        const data = await response.json();
        if (data["message"] == "Yes") {
            noCard.style.display = "none";
            hasCard.style.display = "flex";
            GenerateCardorViewCard();
        }
    }
    hidePreloader();
}

function showPinSetModal() {
    swal({
        text: 'Set 4 Digit Pin',
        content: {
            element: "div",
            attributes: {
                innerHTML:
                    '<input id="pin-input-1" class="swal-input" maxlength="1" placeholder="">' +
                    '<input id="pin-input-2" class="swal-input" maxlength="1" placeholder="">' +
                    '<input id="pin-input-3" class="swal-input" maxlength="1" placeholder="">' +
                    '<input id="pin-input-4" class="swal-input" maxlength="1" placeholder="">',
            },
        },
        button: {
            text: 'Save',
            closeModal: false,
        },
    })
        .then(() => {

            const pin1 = document.getElementById('pin-input-1').value;
            const pin2 = document.getElementById('pin-input-2').value;
            const pin3 = document.getElementById('pin-input-3').value;
            const pin4 = document.getElementById('pin-input-4').value;

           if(!validateInput(pin1) || !validateInput(pin2) || !validateInput(pin3) || !validateInput(pin4)){
            swal({
                title: `Enter 4 Digit Pin`,
                icon: "warning",
            });
            return;
           }

            

            let pin = pin1 + pin2 + pin3 + pin4;

            setPin(pin);
            swal.stopLoading();
            swal.close();
        })
    // Add event listeners to move to the next input field
    const inputFields = document.querySelectorAll('.swal-input');
    inputFields.forEach((input, index) => {
        input.addEventListener('input', (event) => {
            const value = event.target.value;
            if (value !== '' && index < inputFields.length - 1) {
                inputFields[index + 1].focus();
            }
        });
    });

}

async function setPin(pin) {
    showPreloader();

    let jwtToken = localStorage.getItem("jwtToken");

    if (jwtToken == null) {
        location.href = "/login.html";
    }

    const request = {
        pin: pin
    }

    const apiUrl = `${backendUrl}auth/card/set-pin`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        },
        body: JSON.stringify(request),
    });

    if (response.ok) {
        const data = await response.json();
        document.getElementById("setPin").style.display = "none";
        GenerateCardorViewCard();

        swal({
            title: `${data["message"]}`,
            icon: "success",
        });


    }
    else if (response.status == 403) {
        swal({
            title: `${data["message"]}`,
            icon: "error",
        });

    }
    else {
        swal({
            title: `Something Went Wrong`,
            icon: "error",
        });
    }
    hidePreloader();
}

async function changeCardStatus() {

    showPreloader();

    let jwtToken = localStorage.getItem("jwtToken");

    if (jwtToken == null) {
        location.href = "/login.html";
    }

    const apiUrl = `${backendUrl}auth/card/change-status`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    });
    const data = await response.json();
    if (response.ok) {

        swal({
            title: `${data["message"]}`,
            icon: "success",
        });
    }
    else {
        swal({
            title: `${data["message"]}`,
            icon: "warning",
        });
    }
    hidePreloader();
    GenerateCardorViewCard();


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

checkHasCardorNot();
