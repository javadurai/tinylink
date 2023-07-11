function clearModal() {
    document.getElementById('userForm').reset();
    document.getElementById('userModalLabel').innerHTML = 'New User';
    document.getElementById('userId').value = ''; // clear urlId
    document.getElementById('passwordSection').style.display = 'revert';

    // Find the error message div
    let errorMessageDiv = document.getElementById('errorMessage');

    // Add the 'd-none' class to hide the div
    errorMessageDiv.classList.add('d-none');

    // Clear the text content of the div
    errorMessageDiv.textContent = '';
}

function editUser(id, fullName, username, email, role) {
    clearModal();
    document.getElementById('userId').value = id;
    document.getElementById('fullName').value = fullName;
    document.getElementById('username').value = username;
    document.getElementById('email').value = email;
    document.getElementById('role').value = role;
    document.getElementById('passwordSection').style.display = 'none';
    document.getElementById('userModalLabel').innerHTML = 'Edit User';
    var myModal = new bootstrap.Modal(document.getElementById('userModal'));
    myModal.show();
}

function deactivateUser(id) {
    document.getElementById('deactivateUserId').value = id;
    var myModal = new bootstrap.Modal(document.getElementById('deactivateUserModal'));
    myModal.show();
}

function activateUser(id) {
    document.getElementById('activateUserId').value = id;
    var myModal = new bootstrap.Modal(document.getElementById('activateUserModal'));
    myModal.show();
}

function confirmActivateUser() {
    const urlId = document.getElementById('activateUserId').value;

    $.ajax({
        type: "POST",
        url: "/api/users/activate/" + urlId,
        success: function (response) {
            location.reload();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseJSON.message);
        }
    });
}

function confirmDeactivateUser() {
    const urlId = document.getElementById('deactivateUserId').value;

    $.ajax({
        type: "POST",
        url: "/api/users/deactivate/" + urlId,
        success: function (response) {
            location.reload();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseJSON.message);
        }
    });
}

function submitForm() {
    const userId = document.getElementById('userId').value;
    const username = document.getElementById('username').value;
    const fullName = document.getElementById('fullName').value;
    const email = document.getElementById('email').value;
    const role = document.getElementById('role').value;
    const password = document.getElementById('password').value;

    const data = { id: userId, fullName, username, email, role};

    if (userId == ""){
        data.password = password;
    }

    $.ajax({
        type: "POST",
        url: "/api/users",
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
            errorMessageDiv.innerHTML = combinedErrorMessage;                }
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