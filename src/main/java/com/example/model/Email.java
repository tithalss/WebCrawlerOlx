package com.example.model;

public class Email {
    private String toEmail;
    private String subject;
    private String messageText;
    private String attachmentPath;

    public Email(String toEmail, String subject, String messageText, String attachmentPath) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.messageText = messageText;
        this.attachmentPath = attachmentPath;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
}
