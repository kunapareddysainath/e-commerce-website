package com.commerce.student.controller;

import com.commerce.student.model.request.EmailRequest;
import com.commerce.student.model.response.EmailResponse;
import com.commerce.student.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
        public ResponseEntity<?> sendMail(@RequestBody @Valid EmailRequest emailRequest) {

        try {

            if (emailRequest == null || emailRequest.getSenderMailAddress() == null || emailRequest.getBody() == null || emailRequest.getSubject() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide all the required fields to send an email: senderMailAddress, receiverMailAddress, body, and subject.");
            }

            emailService.sendSuggestEmail(emailRequest);

            return ResponseEntity.ok(new EmailResponse<>(200, "Email sent Successfully"));
        } catch (MailException | NullPointerException e) {
            throw new RuntimeException(e);
        }

    }
}
