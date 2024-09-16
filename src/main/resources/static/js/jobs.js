console.log("Jobs.js");
const baseURL = "http://localhost:8080";
const viewJobModal = document.getElementById("view_job_modal");

// options with default values
const options = {
    placement: "bottom-right",
    backdrop: "dynamic",
    backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
    closable: true,
    onHide: () => {
        console.log("modal is hidden");
    },
    onShow: () => {    
        setTimeout(() => {
        jobModal.classList.add("scale-100");
      }, 50);
    },
    onToggle: () => {
        console.log("modal has been toggled");
    },
};

// instance options object
const instanceOptions = {
    id: "view_job_modal",
    override: true,
};

const jobModal = new Modal(viewJobModal, options, instanceOptions);

function openJobModal() {
    jobModal.show();
}

function closeJobModal() {
    jobModal.hide();
}

async function loadJobdata(id) {
    //function call to load data
    console.log('>id', id);
    try {
        const data = await (await fetch(`${baseURL}/api/jobs/${id}`)).json();
        console.log('data->',data);
        document.querySelector("#job_role").innerHTML = data.jobRole;
        document.querySelector("#description").innerHTML = data.description;
        document.querySelector("#applied_date").innerHTML = "Applied On: " + data.appliedDate;
        document.querySelector("#company").innerHTML = data.company + ", " + data.location;
        openJobModal();
    } catch (error) {
        console.log("Error: ", error);
    }
}

// delete 
async function deleteJob(id) {
    Swal.fire({
      title: "Do you want to delete the job application?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Delete",
      confirmButtonColor: "#dc2626",
      cancelButtonColor: "#2563eb"
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        const url = `${baseURL}/user/jobs/delete/` + id;
        window.location.replace(url);
      }
    });
}