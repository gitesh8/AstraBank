let hasCard = document.getElementById("hasCard");
let noCard = document.getElementById("noCard");

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
        noCard.style.display = "none";
        hasCard.style.display = "flex";

        document.getElementById('cardNumber').innerText = data.cardNumber.toString().replace(/(\d{4})/g, "$1 ");
        document.getElementById('cardExpiry').innerText = data.expiry;
        document.getElementById('cardHolder').innerText = data.cardHolderName;
        document.getElementById('cardCVV').innerText = "Cvv: " + data.cvv;
    }
    hidePreloader();
}

async function checkHasCardorNot() {

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

function setPin(pin) {
    console.log(pin);
}

checkHasCardorNot();
