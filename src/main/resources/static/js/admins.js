const firstName = document.getElementById("firstName");
const lastName = document.getElementById("lastName");
const submit = document.getElementById("addAdminBTN");

submit.addEventListener("click", (e) => {
    e.preventDefault();

    const adminObj = {
        firstName: firstName.value,
        lastName: lastName.value
    }

    fetch("/admins", {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect:"error",
        body: JSON.stringify(adminObj)
    })
    .then(res =>{       
        return res.json
    })
    .then(res => {
        console.log(res)
        location.reload()
    })
    .catch(error => console.error(error))
})