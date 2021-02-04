package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
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

    public void contactFormMailSend(User user, String name, String email, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String mailText = String.format(
                "New message from Clod Storage contact form, \n" +
                "Submitted name: %s \n" +
                "User name: %s \n" +
                "Submitted email: %s \n" +
                "User email: %s \n" +
                "Message text: \n" +
                "%s",
                name,
                user.getFirstName() + " " + user.getLastName(),
                email,
                user.getEmail(),
                message
                        );

        mailMessage.setFrom(sender);
        mailMessage.setTo(receiver);
        mailMessage.setSubject("New mail from Cloud Storage contact form: " + subject);
        mailMessage.setText(mailText);

        mailSender.send(mailMessage);
    }
}
