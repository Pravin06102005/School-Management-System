const API = "http://localhost:8080/api/standards";
const token = localStorage.getItem("token");

// Redirect if not logged in
if (!token) window.location = "login.html";

// Authorization header
const authHeader = {
    "Authorization": "Bearer " + token,
    "Content-Type": "application/json"
};

// ----------------------
// Load All Standards
// ----------------------
function loadStandards() {
    fetch(API, {
        method: "GET",
        headers: authHeader
    })
        .then(res => res.json())
        .then(data => {
            let rows = "";
            data.forEach(s => {
                rows += `
                <tr>
                    <td>${s.name}</td>
                    <td>${s.level}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editStandard('${s.id}', '${s.name}', '${s.level}')">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteStandard('${s.id}')">Delete</button>
                    </td>
                </tr>
                `;
            });

            document.getElementById("stdTable").innerHTML = rows;
        })
        .catch(() => alert("Failed to load standards!"));
}

loadStandards();

// ----------------------
// Add or Update Standard
// ----------------------
function saveStandard() {
    let id = document.getElementById("updateId").value;
    let name = document.getElementById("standardName").value;
    let level = document.getElementById("standardLevel").value;

    if (!name || !level) {
        document.getElementById("stdError").innerText = "All fields required!";
        return;
    }

    if (id) {
        updateStandard(id, name, level);
    } else {
        addStandard(name, level);
    }
}

// ----------------------
// Add Standard (POST)
// ----------------------
function addStandard(name, level) {
    fetch(API, {
        method: "POST",
        headers: authHeader,
        body: JSON.stringify({ name, level })
    })
        .then(res => res.json())
        .then(() => {
            resetForm();
            loadStandards();
        })
        .catch(() => alert("Failed to add standard!"));
}

// ----------------------
// Load data into form for editing
// ----------------------
function editStandard(id, name, level) {
    document.getElementById("standardName").value = name;
    document.getElementById("standardLevel").value = level;
    document.getElementById("updateId").value = id;

    document.getElementById("saveBtn").innerText = "Update";
}

// ----------------------
// Update Standard (PUT)
// ----------------------
function updateStandard(id, name, level) {
    fetch(`${API}/${id}`, {
        method: "PUT",
        headers: authHeader,
        body: JSON.stringify({ name, level })
    })
        .then(res => res.json())
        .then(() => {
            resetForm();
            loadStandards();
        })
        .catch(() => alert("Failed to update standard!"));
}

// ----------------------
// Delete Standard
// ----------------------
function deleteStandard(id) {
    if (!confirm("Are you sure?")) return;

    fetch(`${API}/${id}`, {
        method: "DELETE",
        headers: authHeader
    })
        .then(() => loadStandards())
        .catch(() => alert("Delete failed!"));
}

// ----------------------
// Reset Form
// ----------------------
function resetForm() {
    document.getElementById("standardName").value = "";
    document.getElementById("standardLevel").value = "";
    document.getElementById("updateId").value = "";
    document.getElementById("saveBtn").innerText = "Add";
    document.getElementById("stdError").innerText = "";
}

// ----------------------
// Logout
// ----------------------
function logout() {
    localStorage.removeItem("token");
    window.location = "login.html";
}

// Test student fetch (ignored)
fetch("http://localhost:8080/api/students", {
    method: "GET",
    headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
    }
});
