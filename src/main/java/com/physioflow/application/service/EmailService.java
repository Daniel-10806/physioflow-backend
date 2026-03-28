package com.physioflow.application.service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendSessionConfirmation(
            String to,
            String patientName,
            String date,
            String time,
            String type) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("🩺 Confirmación de sesión - PhysioFlow");

            String html = """
                        <div style="font-family: Arial; background:#f4f6f8; padding:20px;">

                            <div style="max-width:600px; margin:auto; background:white; border-radius:10px; overflow:hidden;">

                                <div style="background:#2c7be5; color:white; padding:20px; text-align:center;">
                                    <h2>PhysioFlow</h2>
                                    <p>Confirmación de sesión</p>
                                </div>

                                <div style="padding:20px; color:#333;">

                                    <h3>Hola %s 👋</h3>

                                    <p>Tu sesión ha sido programada correctamente.</p>

                                    <div style="background:#f1f3f5; padding:15px; border-radius:8px;">
                                        <p><strong>📅 Fecha:</strong> %s</p>
                                        <p><strong>⏰ Hora:</strong> %s</p>
                                        <p><strong>💆 Tipo:</strong> %s</p>
                                    </div>

                                    <p style="margin-top:20px;">
                                        Te recomendamos llegar 10 minutos antes.
                                    </p>

                                    <p>¡Te esperamos!</p>

                                </div>

                                <div style="background:#f1f3f5; text-align:center; padding:10px; font-size:12px;">
                                    © PhysioFlow - Sistema de gestión fisioterapéutica
                                </div>

                            </div>

                        </div>
                    """
                    .formatted(patientName, date, time, type);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Enviando correo a: " + to);
    }
}