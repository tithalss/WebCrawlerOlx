package com.example.service;

import com.example.model.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender {
    public void sendEmail(Email email) {
        String fromEmail = "thalesmailtest@yahoo.com";
        String password = "SENHA"; // Use a senha de aplicativo se a autenticação de dois fatores estiver ativada.

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getToEmail()));
            message.setSubject(email.getSubject());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(email.getMessageText());

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(email.getAttachmentPath());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
