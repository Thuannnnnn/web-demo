package com.example.demo.service;

import com.example.demo.model.request.EmailSimpleRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String usernameMail;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMail(EmailSimpleRequest emailRequest) {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(usernameMail);
        mailMessage.setTo(emailRequest.getRecipient());
        mailMessage.setText(emailRequest.getMessageBody());
        mailMessage.setSubject(emailRequest.getSubject());
        mailSender.send(mailMessage);
    }
}
