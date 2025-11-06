// admin/js/admin.js

const API_URL = "http://localhost:8080/api/admin/auth";

// ================== REGISTER ==================
const registerForm = document.getElementById("registerForm");
if (registerForm) {
  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
      const res = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (res.ok) {
        alert("Admin registered successfully!");
        window.location.href = "login.html";
      } else {
        alert("Registration failed. Try again!");
      }
    } catch (err) {
      console.error(err);
      alert("Server error!");
    }
  });
}

// ================== LOGIN ==================
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("loginUsername").value;
    const password = document.getElementById("loginPassword").value;

    try {
      const res = await fetch(`${API_URL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (res.ok) {
        const data = await res.json();
        localStorage.setItem("token", data.token);
        alert("Login successful!");
        window.location.href = "dashboard.html";
      } else {
        alert("Invalid username or password!");
      }
    } catch (err) {
      console.error(err);
      alert("Server error!");
    }
  });
}
