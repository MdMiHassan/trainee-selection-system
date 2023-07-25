package com.example.tss.service;

import com.example.tss.entity.Application;
import com.example.tss.entity.ScreeningRound;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws MessagingException;
    void sendInvitationEmail(String userEmail, Application application);
}
