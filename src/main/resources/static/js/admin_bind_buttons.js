const adminRows = document.getElementsByClassName("admin_row")

for(let adminRow of adminRows){
    const id = adminRow.querySelector(".admin_id");
    const username = adminRow.querySelector(".admin_username")
    const deleteBTN = adminRow.querySelector(".deleteBTN")

    if (deleteBTN != null){
        deleteBTN.addEventListener("click", ()=>{
            fetch('/dashboard/admins/' + id.innerText, {
                method: 'DELETE',
            })
            .then(res => {
                if(res.status = 200){
                    adminRow.remove();
                }
            })
            .catch(err => console.error(err))
        })
    }
}