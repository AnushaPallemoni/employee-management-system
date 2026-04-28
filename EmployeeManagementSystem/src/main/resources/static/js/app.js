/**
 * EMPLOYEE MANAGEMENT SYSTEM - Frontend JavaScript
 *
 * Uses Fetch API to communicate with Spring Boot REST API.
 *
 * API Base URL: http://localhost:8080/api/employees
 */

// Base URL of our Spring Boot backend
const API_URL = "http://localhost:8080/api/employees";

// Global variable: stores the ID of employee to be deleted
let deleteEmployeeId = null;

// =============================================
// On page load: fetch all employees
// =============================================
document.addEventListener("DOMContentLoaded", () => {
    loadAllEmployees();
    loadTotalCount();

    // Allow search on Enter key
    document.getElementById("searchInput").addEventListener("keyup", function (e) {
        if (e.key === "Enter") searchEmployees();
    });
});

// =============================================
// LOAD ALL EMPLOYEES (GET /api/employees)
// =============================================
async function loadAllEmployees() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) throw new Error("Failed to load employees");

        const employees = await response.json();
        renderTable(employees);
        loadTotalCount();
    } catch (error) {
        showAlert("Error loading employees: " + error.message, "error");
    }
}

// =============================================
// SEARCH EMPLOYEES (GET /api/employees/search?keyword=...)
// =============================================
async function searchEmployees() {
    const keyword = document.getElementById("searchInput").value.trim();

    if (!keyword) {
        loadAllEmployees();
        return;
    }

    try {
        const response = await fetch(`${API_URL}/search?keyword=${encodeURIComponent(keyword)}`);
        const employees = await response.json();
        renderTable(employees);
    } catch (error) {
        showAlert("Search failed: " + error.message, "error");
    }
}

// =============================================
// LOAD TOTAL COUNT (GET /api/employees/count)
// =============================================
async function loadTotalCount() {
    try {
        const response = await fetch(`${API_URL}/count`);
        const data = await response.json();
        document.getElementById("totalCount").textContent = data.totalEmployees;
    } catch (error) {
        console.error("Could not load count:", error);
    }
}

// =============================================
// RENDER TABLE ROWS (from employee list)
// =============================================
function renderTable(employees) {
    const tbody = document.getElementById("employeeTableBody");

    if (!employees || employees.length === 0) {
        tbody.innerHTML = `<tr><td colspan="9" class="no-data">No employees found.</td></tr>`;
        return;
    }

    tbody.innerHTML = employees.map(emp => `
        <tr>
            <td><strong>#${emp.id}</strong></td>
            <td>${emp.firstName} ${emp.lastName}</td>
            <td>${emp.email}</td>
            <td>${emp.phoneNumber || "—"}</td>
            <td>${emp.department}</td>
            <td>${emp.jobTitle}</td>
            <td>₹${Number(emp.salary).toLocaleString("en-IN")}</td>
            <td>
                <span class="badge ${emp.status === 'Active' ? 'badge-active' : 'badge-inactive'}">
                    ${emp.status}
                </span>
            </td>
            <td class="actions-cell">
                <button class="btn btn-edit" onclick="openEditModal(${emp.id})">✏️ Edit</button>
                <button class="btn btn-delete" onclick="openDeleteModal(${emp.id}, '${emp.firstName} ${emp.lastName}')">🗑️ Delete</button>
            </td>
        </tr>
    `).join("");
}

// =============================================
// OPEN ADD EMPLOYEE MODAL
// =============================================
function openAddModal() {
    clearForm();
    document.getElementById("modalTitle").textContent = "Add New Employee";
    document.getElementById("saveBtn").textContent = "Save Employee";
    document.getElementById("employeeId").value = "";
    showModal("employeeModal");
}

// =============================================
// OPEN EDIT MODAL (GET /api/employees/{id})
// =============================================
async function openEditModal(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error("Employee not found");

        const emp = await response.json();

        // Fill form with existing data
        document.getElementById("employeeId").value    = emp.id;
        document.getElementById("firstName").value     = emp.firstName;
        document.getElementById("lastName").value      = emp.lastName;
        document.getElementById("email").value         = emp.email;
        document.getElementById("phoneNumber").value   = emp.phoneNumber || "";
        document.getElementById("department").value    = emp.department;
        document.getElementById("jobTitle").value      = emp.jobTitle;
        document.getElementById("salary").value        = emp.salary;
        document.getElementById("status").value        = emp.status;

        document.getElementById("modalTitle").textContent = "Edit Employee";
        document.getElementById("saveBtn").textContent = "Update Employee";
        showModal("employeeModal");

    } catch (error) {
        showAlert("Error loading employee: " + error.message, "error");
    }
}

// =============================================
// SAVE EMPLOYEE (POST = Create, PUT = Update)
// =============================================
async function saveEmployee() {
    // Collect form values
    const id          = document.getElementById("employeeId").value;
    const firstName   = document.getElementById("firstName").value.trim();
    const lastName    = document.getElementById("lastName").value.trim();
    const email       = document.getElementById("email").value.trim();
    const phoneNumber = document.getElementById("phoneNumber").value.trim();
    const department  = document.getElementById("department").value;
    const jobTitle    = document.getElementById("jobTitle").value.trim();
    const salary      = document.getElementById("salary").value;
    const status      = document.getElementById("status").value;

    // Simple frontend validation
    if (!firstName || !lastName || !email || !department || !jobTitle || !salary) {
        showAlert("Please fill all required fields!", "error");
        return;
    }

    // Build employee object (JSON)
    const employeeData = {
        firstName, lastName, email, phoneNumber,
        department, jobTitle,
        salary: parseFloat(salary),
        status
    };

    try {
        let response;

        if (id) {
            // --- UPDATE (PUT) ---
            response = await fetch(`${API_URL}/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(employeeData)
            });
        } else {
            // --- CREATE (POST) ---
            response = await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(employeeData)
            });
        }

        const result = await response.json();

        if (response.ok) {
            closeModal();
            loadAllEmployees();
            showAlert(id ? "Employee updated successfully!" : "Employee added successfully!", "success");
        } else {
            // Show server-side error
            const errorMsg = result.message || result.fieldErrors
                ? JSON.stringify(result.fieldErrors || result.message)
                : "Something went wrong!";
            showAlert("Error: " + errorMsg, "error");
        }

    } catch (error) {
        showAlert("Request failed: " + error.message, "error");
    }
}

// =============================================
// OPEN DELETE CONFIRMATION MODAL
// =============================================
function openDeleteModal(id, name) {
    deleteEmployeeId = id;
    document.getElementById("deleteEmployeeName").textContent = name;
    showModal("deleteModal");
}

// =============================================
// CONFIRM DELETE (DELETE /api/employees/{id})
// =============================================
async function confirmDelete() {
    if (!deleteEmployeeId) return;

    try {
        const response = await fetch(`${API_URL}/${deleteEmployeeId}`, {
            method: "DELETE"
        });

        const result = await response.json();

        if (response.ok) {
            closeDeleteModal();
            loadAllEmployees();
            showAlert(result.message, "success");
        } else {
            showAlert("Delete failed: " + result.message, "error");
        }

    } catch (error) {
        showAlert("Delete failed: " + error.message, "error");
    }

    deleteEmployeeId = null;
}

// =============================================
// MODAL HELPERS
// =============================================
function showModal(modalId) {
    document.getElementById(modalId).classList.remove("hidden");
}
function closeModal() {
    document.getElementById("employeeModal").classList.add("hidden");
    clearForm();
}
function closeDeleteModal() {
    document.getElementById("deleteModal").classList.add("hidden");
    deleteEmployeeId = null;
}
function clearForm() {
    ["employeeId","firstName","lastName","email","phoneNumber","jobTitle","salary"].forEach(id => {
        document.getElementById(id).value = "";
    });
    document.getElementById("department").value = "";
    document.getElementById("status").value = "Active";
}

// =============================================
// SHOW ALERT MESSAGE
// =============================================
function showAlert(message, type) {
    const alertBox = document.getElementById("alertBox");
    alertBox.textContent = message;
    alertBox.className = `alert ${type}`;
    alertBox.classList.remove("hidden");

    // Auto-hide after 4 seconds
    setTimeout(() => alertBox.classList.add("hidden"), 4000);
}
