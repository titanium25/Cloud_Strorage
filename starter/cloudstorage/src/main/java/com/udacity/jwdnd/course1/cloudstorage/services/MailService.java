package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.servername}")
    private String sender;

    @Value("${spring.mail.receivername}")
    private String receiver;

    public void send(String subject,String mail, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(receiver);
        mailMessage.setSubject("New mail from" + subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
