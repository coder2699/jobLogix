console.log("Jobs.js");
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
        console.log("modal is shown");
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
        const data = await (
            await fetch(`http://localhost:8080/api/jobs/${id}`)
        ).json();
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