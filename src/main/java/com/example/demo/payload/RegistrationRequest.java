package com.example.demo.payload;

public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String dob;     // new
    private String address; // new
    private String password;
    private String roleName;
    private String username;
    private Integer twoFaEnabled;

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getTwoFaEnabled() {
        return twoFaEnabled;
    }

    public void setTwoFaEnabled(Integer twoFaEnabled) {
        this.twoFaEnabled = twoFaEnabled;
    }
}
