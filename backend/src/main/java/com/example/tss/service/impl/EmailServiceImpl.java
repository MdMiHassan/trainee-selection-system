package com.example.tss.service.impl;

import com.example.tss.constants.molds.HTMLMold;
import com.example.tss.entity.Application;
import com.example.tss.entity.Circular;
import com.example.tss.entity.ScreeningRound;
import com.example.tss.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendInvitationEmail(String userEmail, Application application) {
        try {
            ScreeningRound currentRound = application.getCurrentRound();
            Circular circular = application.getCircular();
            String screeningInvitationEmail = HTMLMold.SCREENING_INVITATION_EMAIL;
            Document document = Jsoup.parse(screeningInvitationEmail);
            String applicantName = application.getFirstName() + " " + application.getLastName();
            document.getElementById("applicant-name").text(applicantName);
            document.getElementById("job-position").text(circular.getTitle());
            String currentRoundTitle = currentRound.getTitle();
            if (currentRoundTitle != null) {
                document.getElementById("round-name").text(currentRound.getTitle());
            }
            String location = currentRound.getLocation();
            if (location != null) {
                document.getElementById("round-location").text(currentRound.getLocation());
            }
            Timestamp examTime = currentRound.getExamTime();
            if (examTime != null) {
                document.getElementById("round-date").text(examTime.toString());
            }
            Boolean requiredAdmitCard = currentRound.getRequiredAdmitCard();
            if (requiredAdmitCard != null && requiredAdmitCard) {
                document.getElementById("round-location").text("Please visit and download admit card from the portal");
            }
            String body = document.html();
            sendEmail(userEmail, "You have been selected for the " + currentRound.getTitle(), body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

