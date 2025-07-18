package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin-test")
@CrossOrigin(origins = "*") // 🔁 Allow all origins
public class AdminTestController {

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        System.out.println("✅ Approve endpoint called with ID: " + id);
        return "Approved: " + id;
    }

    @PostMapping("/deny/{id}")
    public String deny(@PathVariable Long id) {
        System.out.println("❌ Deny endpoint called with ID: " + id);
        return "Denied: " + id;
    }

    @GetMapping("/ping")
    public String ping() {
        return "Admin test controller is active";
    }
}
