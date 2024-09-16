const selectField = document.getElementById('searchFields');
const searchInput = document.getElementById('searchInput');
const searchButton = document.getElementById('searchButton');
// Function to update button style when disabled or enabled
function updateButtonState() {
    if (selectField.value === "") {
        searchInput.disabled = true;
        searchInput.style.opacity = "0.5";
        searchInput.style.cursor = "not-allowed";
        searchInput.value = "";
    } else {
        searchInput.disabled = false;
        searchInput.style.opacity = "1";
        searchInput.style.cursor = "auto";
    }
}
searchButton.style.cursor = "pointer";


// Attach the event listener to the select field
selectField.addEventListener('change', updateButtonState);

// Check the initial state of the button on page load
window.onload = updateButtonState;