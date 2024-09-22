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

function formatDate(dateString) {
    // Create a Date object from the input string
    const date = new Date(dateString);

    // Define options for formatting the date
    const options = { day: 'numeric', month: 'long', year: 'numeric' };

    // Format the date using toLocaleDateString
    return date.toLocaleDateString('en-US', options);
}

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
        document.querySelector("#picture").src = data.picture;
        document.querySelector("#company").innerHTML = data.company;
        document.querySelector("#jobRole").innerHTML = data.jobRole;
        document.querySelector("#location").innerHTML = data.location;
        document.querySelector("#platform").innerHTML = 'Applied On ' + data.platform;
        document.querySelector("#appliedDate").innerHTML = formatDate(data.appliedDate);
        document.querySelector("#referral").innerHTML = data.referred? 'Referred By '+ data.referredBy:'Referred By None';
        document.querySelector("#starred").innerHTML = data.starred?'🟢 Active Job Application':'🔴 Inactive Job Application';
        document.querySelector("#currentStatus").innerHTML = data.currentStatus;
        document.querySelector("#description").innerHTML = data.description;
        document.querySelector("#jobLink").innerHTML = data.jobLink;
        document.querySelector("#jobLink").href = data.jobLink;
        document.querySelector("#cvLink").innerHTML = data.cvLink;
        document.querySelector("#cvLink").href = data.cvLink;
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