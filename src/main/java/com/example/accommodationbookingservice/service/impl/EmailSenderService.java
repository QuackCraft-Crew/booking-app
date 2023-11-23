package com.example.accommodationbookingservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendHtmlEmail(String toEmail,
                              String subject,
                              Map<String, Object> templateModel) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("servicebooking7@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);

        String htmlContent = templateEngine.process("mailFrontEnd.html",
                new Context(Locale.getDefault(), templateModel));
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
