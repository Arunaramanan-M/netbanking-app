function showLogin() {
  document.getElementById('login-form').classList.remove('hidden');
  document.getElementById('register-form').classList.add('hidden');
}

function showRegister() {
  document.getElementById('register-form').classList.remove('hidden');
  document.getElementById('login-form').classList.add('hidden');
}

const username = localStorage.getItem("loggedInUser") || "Customer";
document.getElementById("welcome-message").innerText = `Welcome, ${username}!`;


// Registration Flow without OTP
async function startRegistration(event) {
  event.preventDefault();

  const fullName = document.getElementById('reg-name').value.trim();
  const firstName = fullName.split(" ")[0];
  const lastName = fullName.split(" ").slice(1).join(" ") || "N/A";
  const enable2FA = document.getElementById('reg-2fa')?.checked || false;

  const requestBody = {
    firstName: firstName,
    lastName: lastName,
    email: document.getElementById('reg-email').value,
    mobile: document.getElementById('reg-phone').value,
    dob: document.getElementById('reg-dob').value,
    address: document.getElementById('reg-address').value,
    password: document.getElementById('reg-password').value,
    username: document.getElementById('reg-username').value,
    roleName: "Customer",
    twoFaEnabled: enable2FA ? 1 : 0
  };


  try {
    const response = await fetch("/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(requestBody)
    });

    const message = await response.text();
    alert(message);

    if (response.ok && message.toLowerCase().includes("successful")) {
      showLogin();
    }
  } catch (error) {
    alert("Registration failed: " + error.message);
  }
}

// Login Flow (still simulated)
async function startLogin(event) {
  event.preventDefault();

  const username = document.getElementById('login-username').value;
  const password = document.getElementById('login-password').value;

  const requestBody = {
    username: username,
    password: password
  };

  try {
    const response = await fetch("/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(requestBody)
    });

    const result = await response.text();

    if (response.ok) {
      if (result.includes("2FA required")) {
        alert("2FA is enabled. OTP sent to your email.");
        document.getElementById('login-form').classList.add('hidden');
        document.getElementById('login-2fa-form').classList.remove('hidden');

        // Store username temporarily for 2FA verification
        localStorage.setItem('2faUser', requestBody.username);
      } else if (result === 'Login successful') {
               alert("Login successful!");
               localStorage.setItem("loggedInUser", username); // <-- ADD THIS
               window.location.href = "dashboard.html";
             }
    } else {
      alert(result);
    }
  } catch (error) {
    alert("Login failed: " + error.message);
  }
}

async function verifyLogin2FA(event) {
  event.preventDefault();

  const code = document.getElementById('login-2fa-code').value;
  const username = localStorage.getItem('2faUser');

  const requestBody = {
    username: username,
    code: code
  };

  try {
    const response = await fetch("/api/auth/verify-2fa", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(requestBody)
    });

    const result = await response.text();

    if (response.ok && result.includes("Login successful")) {
      alert("Login successful!");
      localStorage.removeItem('2faUser');
      localStorage.setItem("loggedInUser", username); // <-- ADD THIS
      window.location.href = "dashboard.html";
    } else {
      alert(result);
    }
  } catch (error) {
    alert("2FA verification failed: " + error.message);
  }
}

function goToAdminLogin() {
    window.location.href = "/admin-login.html"; // change to your actual admin login page
}

