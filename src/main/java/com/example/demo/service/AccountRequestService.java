package com.example.demo.service;

import com.example.demo.model.AccountRequest;
import com.example.demo.model.BankAccount;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRequestRepository;
import com.example.demo.repository.BankAccountRepository;
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
    private BankAccountRepository bankAccountRepo;

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

        String accNum = "AC" + UUID.randomUUID().toString().replaceAll("[^A-Z0-9]", "").substring(0, 10).toUpperCase();
        request.setAccountNumber(accNum);

        // 🔥 CREATE BANK ACCOUNT FOR USER
        BankAccount account = new BankAccount();
        account.setUser(request.getUser());
        account.setAccountNumber(accNum);
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialDeposit());
        account.setStatus("ACTIVE");

        bankAccountRepo.save(account);

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
