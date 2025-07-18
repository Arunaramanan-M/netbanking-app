package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT_REQUESTS")
public class AccountRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountType; // e.g. savings, current etc.
    private Double initialDeposit;
    private String status = "PENDING"; // PENDING | APPROVED | DENIED

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime requestedAt = LocalDateTime.now();

    private String accountNumber; // Will be filled after approval

    // Getters and Setters...
    public Long getId() { return id; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public Double getInitialDeposit() { return initialDeposit; }
    public void setInitialDeposit(Double initialDeposit) { this.initialDeposit = initialDeposit; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}
