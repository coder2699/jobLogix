
const isReferredCheckbox = document.getElementById('isReferred');
const referredByInput = document.getElementById('referredBy');
referredByInput.disabled = !isReferredCheckbox.checked;

function disableInput() {
    if (!isReferredCheckbox.checked) {
        referredByInput.style.cursor = "not-allowed";
        referredByInput.disabled = true;
        referredByInput.value = "";
        referredByInput.style.opacity = "0.5";
    } else {
        referredByInput.style.cursor = "auto";
        referredByInput.disabled = false;
        referredByInput.style.opacity = "1";
    }
}

// Add event listener to the checkbox
isReferredCheckbox.addEventListener('change', disableInput);

// Check the initial state of the button on page load
window.onload = disableInput;
