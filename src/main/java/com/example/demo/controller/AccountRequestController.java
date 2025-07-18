package com.example.demo.controller;

import com.example.demo.model.AccountRequest;
import com.example.demo.service.AccountRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin
public class AccountRequestController {

    @Autowired
    private AccountRequestService requestService;

    @PostMapping("/create")
    public String createRequest(@RequestParam String username,
                                @RequestParam String accountType,
                                @RequestParam Double depositAmount) {
        boolean success = requestService.createRequest(username, accountType, depositAmount);
        return success ? "Account request submitted" : "User not found";
    }

    @GetMapping("/requests")
    public List<AccountRequest> getAllRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        return requestService.approveRequest(id) ? "Approved" : "Invalid ID";
    }

    @PostMapping("/deny/{id}")
    public String deny(@PathVariable Long id) {
        return requestService.denyRequest(id) ? "Denied" : "Invalid ID";
    }
}
