package com.example.demo.service;

import com.example.demo.model.AccountRequest;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRequestRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountRequestService {

    @Autowired
    private AccountRequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean createRequest(String username, String accountType, Double depositAmount) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return false;

        AccountRequest req = new AccountRequest();
        req.setUser(userOpt.get());
        req.setAccountType(accountType);
        req.setInitialDeposit(depositAmount);
        requestRepository.save(req);

        return true;
    }

    public List<AccountRequest> getPendingRequests() {
        return requestRepository.findByStatus("PENDING");
    }

    public List<AccountRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @Transactional
    public boolean approveRequest(Long id) {
        Optional<AccountRequest> reqOpt = requestRepository.findById(id);
        if (reqOpt.isEmpty()) return false;
        AccountRequest request = reqOpt.get();
        request.setStatus("APPROVED");

        // Generate random unique account number
        String accNum = "AC" + UUID.randomUUID().toString().substring(0, 10).replace("-", "").toUpperCase();
        request.setAccountNumber(accNum);
        return true;
    }

    @Transactional
    public boolean denyRequest(Long id) {
        Optional<AccountRequest> reqOpt = requestRepository.findById(id);
        if (reqOpt.isEmpty()) return false;

        AccountRequest request = reqOpt.get();
        request.setStatus("DENIED");
        return true;
    }
}
