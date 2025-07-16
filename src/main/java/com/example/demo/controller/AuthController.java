//package com.example.demo.controller;
//
//import com.example.demo.model.User;
//import com.example.demo.payload.RegistrationRequest;
//import com.example.demo.service.EmailService;
//import com.example.demo.service.OtpService;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.example.demo.dto.LoginRequest;
//import java.time.LocalDate;
//import com.example.demo.payload.TwoFARequest;
//
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private OtpService otpService;
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping("/register")
//    public String register(@RequestBody RegistrationRequest req) {
//        User user = new User();
//        user.setFirstName(req.getFirstName());
//        user.setLastName(req.getLastName());
//        user.setEmail(req.getEmail());
//        user.setMobile(req.getMobile());
//        user.setDob(LocalDate.parse(req.getDob())); // parses ISO date string to LocalDate
//        user.setAddress(req.getAddress());
//        user.setPasswordHash(req.getPassword());
//        user.setUsername(req.getUsername());
//        user.setTwoFaEnabled(req.getTwoFaEnabled() != null ? req.getTwoFaEnabled() : 0);
//        boolean success = userService.register(user, req.getRoleName());
//        return success ? "Registration successful" : "User already exists";
//    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
//        User user = userService.findByUsername(req.getUsername());
//        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        if (user.getTwoFaEnabled() == 1) {
//            String otp = otpService.generateOtp(user.getUsername());
//            emailService.sendOtpEmail(user.getEmail(), otp);
//            return ResponseEntity.ok("2FA required. OTP sent to your email.");
//        }
//
//        return ResponseEntity.ok("Login successful");
//    }
//    @PostMapping("/verify-2fa")
//    public ResponseEntity<String> verify2FA(@RequestBody TwoFARequest req) {
//        User user = userService.findByUsername(req.getUsername());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
//        }
//
//        boolean valid = otpService.validateOtp(user.getUsername(), req.getCode());
//        if (!valid) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
//        }
//
//        return ResponseEntity.ok("Login successful");
//    }
//
//
//
//}



package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.payload.RegistrationRequest;
import com.example.demo.service.EmailService;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.LoginRequest;
import java.time.LocalDate;
import com.example.demo.payload.TwoFARequest;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest req) {
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setDob(LocalDate.parse(req.getDob())); // parses ISO date string to LocalDate
        user.setAddress(req.getAddress());
        user.setPasswordHash(req.getPassword());
        user.setUsername(req.getUsername());
        user.setTwoFaEnabled(req.getTwoFaEnabled() != null ? req.getTwoFaEnabled() : 0);
        boolean success = userService.register(user, req.getRoleName());
        return success ? "Registration successful" : "User already exists";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (user.getTwoFaEnabled() == 1) {
            // Generate and send OTP via email
            otpService.generateAndSendOtp(user.getUsername(), user.getEmail());

            return ResponseEntity.ok("2FA required. OTP sent to your email.");
        }

        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<String> verify2FA(@RequestBody TwoFARequest req) {
        User user = userService.findByUsername(req.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        // Validate the OTP
        boolean otpValid = otpService.validateOtp(req.getUsername(), req.getCode());

        if (!otpValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
        }

        return ResponseEntity.ok("Login successful");
    }

}
