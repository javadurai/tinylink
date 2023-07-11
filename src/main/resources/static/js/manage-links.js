function clearModal() {
    document.getElementById('urlForm').reset();
    document.getElementById('urlModalLabel').innerHTML = 'New URL';
    document.getElementById('urlId').value = ''; // clear urlId

    // Find the error message div
    let errorMessageDiv = document.getElementById('errorMessage');

    // Add the 'd-none' class to hide the div
    errorMessageDiv.classList.add('d-none');

    // Clear the text content of the div
    errorMessageDiv.textContent = '';
}

function editUrl(id, shortUrl, originalUrl) {
    clearModal();
    document.getElementById('urlId').value = id;
    document.getElementById('shortUrl').value = shortUrl;
    document.getElementById('originalUrl').value = originalUrl;
    document.getElementById('urlModalLabel').innerHTML = 'Edit URL';
    var myModal = new bootstrap.Modal(document.getElementById('urlModal'));
    myModal.show();
}

function deleteUrl(id) {
    document.getElementById('deleteUrlId').value = id;
    var myModal = new bootstrap.Modal(document.getElementById('deleteConfirmationModal'));
    myModal.show();
}

function confirmDeleteLink() {
    const urlId = document.getElementById('deleteUrlId').value;

    $.ajax({
        type: "DELETE",
        url: "/api/urls/" + urlId,
        success: function (response) {
            location.reload(); // or update the table using JavaScript

            // Find the error message div
            let errorMessageDiv = document.getElementById('errorMessage');

            // Add the 'd-none' class to hide the div
            errorMessageDiv.classList.add('d-none');

            // Clear the text content of the div
            errorMessageDiv.textContent = '';
        },
        error: function (xhr, status, error) {
            // Find the error message div and update it
            let errorMessageDiv = document.getElementById('errorMessage');

            // Remove the 'd-none' class to make the div visible
            errorMessageDiv.classList.remove('d-none');

            // Create a combined error message
            let combinedErrorMessage = '';
            for (let i = 0; i < xhr.responseJSON.length; i++) {
                combinedErrorMessage += xhr.responseJSON[i].defaultMessage + '<br>';
            }

            // Update the inner HTML of the div
            errorMessageDiv.innerHTML = combinedErrorMessage;
        }
    });
}

function submitForm() {
    const urlId = document.getElementById('urlId').value;
    const shortUrl = document.getElementById('shortUrl').value;
    const originalUrl = document.getElementById('originalUrl').value;

//    console.log(urlId);

    const data = { id: urlId, shortUrl, originalUrl };

//    console.log(JSON.stringify(data));

    $.ajax({
        type: "POST",
        url: "/api/urls",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (response) {
            location.reload(); // or update the table using JavaScript

            // Find the error message div
            let errorMessageDiv = document.getElementById('errorMessage');

            // Add the 'd-none' class to hide the div
            errorMessageDiv.classList.add('d-none');

            // Clear the text content of the div
            errorMessageDiv.textContent = '';
        },
        error: function (xhr, status, error) {
            // Find the error message div and update it
            let errorMessageDiv = document.getElementById('errorMessage');

            // Remove the 'd-none' class to make the div visible
            errorMessageDiv.classList.remove('d-none');

            // Create a combined error message
            let combinedErrorMessage = '';
            for (let i = 0; i < xhr.responseJSON.length; i++) {
                combinedErrorMessage += xhr.responseJSON[i].defaultMessage + '<br>';
            }

            // Update the inner HTML of the div
            errorMessageDiv.innerHTML = combinedErrorMessage;
        }
    });
}

function saveOwnershipChanges() {
    // Logic to save the changes in ownership
    // This could involve sending the data to the server
    alert('Ownership changes saved'); // This is just a placeholder
}

$(document).ready(function () {
    $('.owner-selection').select2();
});