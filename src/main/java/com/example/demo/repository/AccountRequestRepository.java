package com.example.demo.repository;

import com.example.demo.model.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRequestRepository extends JpaRepository<AccountRequest, Long> {
    List<AccountRequest> findByStatus(String status);
}
