package com.physioflow.application.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSessionConfirmation(
            String to,
            String patientName,
            String date,
            String time,
            String type) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Confirmación de sesión de fisioterapia");

        message.setText(
                "Hola " + patientName + ",\n\n" +
                        "Tu sesión ha sido programada.\n\n" +
                        "Fecha: " + date + "\n" +
                        "Hora: " + time + "\n" +
                        "Tipo: " + type + "\n\n" +
                        "Te esperamos.\n\n" +
                        "PhysioFlow");

        mailSender.send(message);
    }
}