// ===============================
// 🔐 SAVE TOKEN HELPER
// ===============================
function getAuthHeader() {
    const token = localStorage.getItem("token");
    return token ? { "Authorization": "Bearer " + token } : {};
}

// ===============================
// 🔑 LOGIN FUNCTION (FIXED)
// ===============================
function login() {
    let user = document.getElementById("username").value.trim();
    let pass = document.getElementById("password").value.trim();

    fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: user, password: pass })
    })
    .then(async res => {

        // 🔥 Reject non-200 responses
        if (!res.ok) {
            document.getElementById("error").innerText = "Invalid username or password!";
            return;
        }

        const token = await res.text();

        // 🔥 Token must be long enough to be valid
        if (!token || token.length < 30) {
            document.getElementById("error").innerText = "Invalid token received!";
            return;
        }

        // Save token
        localStorage.setItem("token", token);

        alert("Login Successful!");
        window.location.href = "index.html";
    })
    .catch(err => {
        document.getElementById("error").innerText = "Server error!";
    });
}





// ===============================
// 📝 REGISTER FUNCTION (FIXED)
// ===============================
async function register() {
    const payload = {
        name: document.getElementById("name").value.trim(),
        username: document.getElementById("username").value.trim(),
        email: document.getElementById("email").value.trim(),
        schoolName: document.getElementById("school").value.trim(), // ✅ FIXED ID
        password: document.getElementById("password").value.trim()
    };

    try {
        const res = await fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await res.json();
        console.log("REGISTER RESPONSE:", data);

        if (res.ok) {
            alert("Registration Successful!");
            window.location.href = "login.html";
        } else {
            document.getElementById("error").textContent =
                data.error || "Something went wrong!";
        }
    } catch (err) {
        document.getElementById("error").textContent = "Network error!";
    }
}
