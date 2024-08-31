package com.commerce.student.service;

import com.commerce.student.model.request.EmailRequest;
import com.commerce.student.utility.Constants;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    @Value("${app.mail}")
    private String receiverMailAddress;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JobScheduler jobScheduler;

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    @Job(name = "SendEmail")
    public void sendEmail(SimpleMailMessage message) {
        mailSender.send(message);
    }

    private SimpleMailMessage createMessage(String from, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    private String generateEmailBody(String customerName, int orderNumber, String dateTime, String status) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customerName).append(",\n\n");
        sb.append("Your order with order number ").append(orderNumber);
        sb.append(", created on ").append(dateTime).append(" has been ").append(status).append(".\n\n");
        sb.append("Thank you for shopping with us!\n\n");
        sb.append("Best regards,\n");
        sb.append("Student Ecommerce.");
        return sb.toString();
    }

    public void sendSuggestEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = createMessage(
                emailRequest.getSenderMailAddress(),
                receiverMailAddress,
                emailRequest.getSubject(),
                emailRequest.getBody()
        );
        jobScheduler.enqueue(()-> sendEmail(message));
    }

    public void orderEmail(String customerName, String emailAddress, int orderNumber, Date createdAt) {
        String formattedDateTime = formatDate(createdAt);
        String body = generateEmailBody(customerName, orderNumber, formattedDateTime, "successfully created");
        SimpleMailMessage message = createMessage(
                receiverMailAddress,
                emailAddress,
                Constants.CREATED_ORDER_EMAIL_SUBJECT,
                body
        );
        jobScheduler.enqueue(()-> sendEmail(message));
    }

    public void orderAcceptedEmail(String customerName, String emailAddress, int orderNumber, Date createdAt) {
        String formattedDateTime = formatDate(createdAt);
        String body = generateEmailBody(customerName, orderNumber, formattedDateTime, "accepted");
        SimpleMailMessage message = createMessage(
                receiverMailAddress,
                emailAddress,
                Constants.ACCEPTED_ORDER_EMAIL_SUBJECT,
                body
        );
       jobScheduler.enqueue(()-> sendEmail(message));
    }

    public void orderCancelledEmail(String customerName, String emailAddress, int orderNumber, Date createdAt) {
        String formattedDateTime = formatDate(createdAt);
        String body = generateEmailBody(customerName, orderNumber, formattedDateTime, "cancelled");
        SimpleMailMessage message = createMessage(
                receiverMailAddress,
                emailAddress,
                Constants.CANCELLED_ORDER_EMAIL_SUBJECT,
                body
        );
        jobScheduler.enqueue(()-> sendEmail(message));
    }

    public void orderDeliveredEmail(String customerName, String emailAddress, int orderNumber, Date createdAt) {
        String formattedDateTime = formatDate(createdAt);
        String body = generateEmailBody(customerName, orderNumber, formattedDateTime, "delivered");
        SimpleMailMessage message = createMessage(
                receiverMailAddress,
                emailAddress,
                Constants.DELIVERED_ORDER_EMAIL_SUBJECT,
                body
        );
        jobScheduler.enqueue(()-> sendEmail(message));
    }
}
