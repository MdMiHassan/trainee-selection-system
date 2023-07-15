package com.example.tss.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws MessagingException;
    void sendEmail(String to,String[] cc, String subject, String body) throws MessagingException;
    void sendEmail(String to,String[] cc,String[] bcc, String subject, String body) throws MessagingException;
}
