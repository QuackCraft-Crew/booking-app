package com.example.accommodationbookingservice.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class HealthController {
    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/health")
    public String healthCheck() {
        String htmlContent = templateEngine.process("test.html",
                new Context(Locale.getDefault()));
        return htmlContent;
    }
}
