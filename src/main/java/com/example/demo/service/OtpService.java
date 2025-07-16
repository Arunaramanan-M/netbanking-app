package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Autowired
    private EmailService emailService;

    public void generateAndSendOtp(String username, String email) {
        String otp = String.format("%06d", random.nextInt(999999));
        otpStorage.put(username, otp);
        emailService.sendOtpEmail(email, otp);
    }

    public boolean validateOtp(String username, String code) {
        String correctOtp = otpStorage.get(username);
        if (correctOtp != null && correctOtp.equals(code)) {
            otpStorage.remove(username);
            return true;
        }
        return false;
    }
}
