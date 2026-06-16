package com.example.aereopuerto.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String toEmail, String verificationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verificación de Cuenta - Aerolínea");
        
        String verificationUrl = "https://aerogest.ddns.net/verify.html?token=" + verificationToken;
        
        message.setText("Hola,\n\n" +
                "Gracias por registrarte en nuestra plataforma. " +
                "Por favor, verifica tu cuenta haciendo click en el siguiente enlace:\n\n" +
                verificationUrl + "\n\n" +
                "Saludos,\nEl equipo de AeroGest");
        
        mailSender.send(message);
    }

    @Async
    public void sendTwoFactorCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Código de Autenticacion 2FA");
        
        message.setText("Hola,\n\n" +
                "Tu código de autenticacion de dos factores es:\n\n" +
                code + "\n\n" +
                "Si no intentaste iniciar sesion, ignora este mensaje.\n\n" +
                "Saludos,\nEl equipo de AeroGest");
        
        mailSender.send(message);
    }
}
