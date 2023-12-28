var mediaQuery = window.matchMedia("(max-width: 914px)");
let jwtToken = localStorage.getItem("jwtToken");
async function SendOtpToEmail(email) {

    showPreloader();

    const formData = {
        email: email,
    };

    // TODO: Replace the following placeholder URL with your actual backend API endpoint
    const apiUrl = `${backendUrl}email/email-verification`;

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
    if (data["status"] == true) {
        localStorage.setItem("otpId", data["message"]);
        showOtpVerificaiton();
    }
    else {
        swal({
            title: data["message"],
            icon: "warning",
        })
            .then((showAgain) => {
                EmailVerfication()
            });

    }
}

async function VerifyOtp(otp) {
    let otpId = localStorage.getItem("otpId");

    showPreloader();

    const formData = {
        otp: otp.trim(),
        otpId: otpId.trim()
    };

    // TODO: Replace the following placeholder URL with your actual backend API endpoint
    const apiUrl = `${backendUrl}email/verify-email-otp`;

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
    if (data["status"] == true) {
        localStorage.removeItem("otpId");
        swal({
            title: data["message"],
            icon: "success",
        });
        getUserDetails();

    }
    else if (response.status == 200) {
        swal({
            title: data["message"],
            icon: "warning",
        }).then((showAgain) => {
            // again showing otp window
            showOtpVerificaiton();
        });;


    }
    else if (response.status == 403) {
        swal({
            title: data["message"],
            icon: "warning",
        }).then((showAgain) => {
            // again showing otp window
            showOtpVerificaiton();
        });;;
    }
    else {
        swal({
            title: "Something Went Wrong",
            icon: "error",
        });
    }
}

function showOtpVerificaiton() {
    swal({
        text: 'Verify Otp',
        content: {
            element: "div",
            attributes: {
                innerHTML:
                    '<span>A-</span><input type="text" id="email-verification-otp" class="swal-input"  placeholder="Enter OTP ">'
            },
        },
        button: {
            text: 'Save',
            closeModal: false,
        },
        closeOnClickOutside: false
    })
        .then(() => {

            const otp = document.getElementById('email-verification-otp').value;
            

            if (!validateInput(otp)) {
                swal({
                    title: `Enter Valid OTP`,
                    icon: "warning",
                }).then((showAgain) => {
                    // again showing otp window
                    showOtpVerificaiton();
                });
                return;
            }
            VerifyOtp(otp);
            swal.stopLoading();
            swal.close();
        })
}

function EmailVerfication() {
    swal({
        text: 'Email Verification',
        content: {
            element: "div",
            attributes: {
                innerHTML:
                    '<input type="email" id="email-verification" class="swal-input"  placeholder="Enter Email ">'
            },
        },
        button: {
            text: 'Save',
            closeModal: false,
        },
        closeOnClickOutside: false
    })
        .then(() => {

            const email = document.getElementById('email-verification').value;
           

            if (!validateInput(email)) {
                swal({
                    title: `Enter Valid Email`,
                    icon: "warning",
                }).then((showAgain) => {
                    // again showing otp window
                    EmailVerfication();
                });
                return;
            }
            SendOtpToEmail(email);
            swal.stopLoading();
            swal.close();
        })
}
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

        if (responseData["customer"]["email"] != null) {
            document.getElementById("username").innerText = `Welcome, ${responseData["customer"]["firstName"]}`;
            document.getElementById("accBalance").innerText = `Rs ${responseData["balance"]}`;
        }
        else {
            EmailVerfication();
        }


    }
    else {
        logout();
    }

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


getUserDetails();