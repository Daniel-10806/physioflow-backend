package com.physioflow.application.service;

import com.physioflow.domain.model.enumtype.NotificationType;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final EmailService emailService;
    private final NotificationMessageBuilder builder;

    public NotificationService(
            EmailService emailService,
            NotificationMessageBuilder builder) {

        this.emailService = emailService;
        this.builder = builder;
    }

    public void send(
            NotificationType type,
            String email,
            String patient,
            String date,
            String time,
            String extra) {

        String subject = builder.buildSubject(type);

        String body = builder.buildBody(
                type,
                patient,
                date,
                time,
                extra);

        switch (type) {

            case SESSION_CREATED -> emailService.sendSessionConfirmation(
                    email,
                    patient,
                    date,
                    time,
                    extra);

            default -> emailService.sendEmail(
                    email,
                    subject,
                    body);
        }
    }
}