async function getTransactions(){

    let jwtToken = localStorage.getItem("jwtToken");

    if(jwtToken==null){
        location.href="/login.html";
    }

    const apiUrl = `${backendUrl}auth/transactions`;

    // Simulate a POST request to the backend API
    const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    });

    if(response.ok){
        const data = await response.json();
        
        const tbody = document.querySelector('#dataTable tbody');

        // Clear existing rows
        tbody.innerHTML = '';


        data.forEach(el => {
            const row =`<tr>
             <td>${el.fromAccountNumber}</td>
             <td>${el.transactionType}</td>
             <td>${el.amount}</td>
             <td>${el.transactionStatus}</td>
             <td>${el.transactionMode}</td>
             <td>${el.remark}</td>
            </tr>`
            tbody.innerHTML+=row;
        });

    }
}

getTransactions();