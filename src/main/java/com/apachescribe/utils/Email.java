package com.apachescribe.utils;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;

// @Configuration
public class Email {

    private static final Logger log = Logger.getLogger(Email.class);

    // @Value("${mail-from}")
    private String mailFrom;

    // @Value("${mail-to}")
    private String mailTo;

    // @Value("${mail-to-cc}")
    private String mailToCc;

    // @Value("${mail-subject}")
    private String mailSubject;

    // @Value("${mail-content}")
    private String mailContent;

    // @Value("${mail-host}")
    private String mailHost;

    // @Value("${mail-port}")
    private String mailPort;

    public Boolean sendMailWithAttachment(File[] a, String extension) {

        Boolean mailSentSuccessfully = false;

        log.info("Begin mail sending");
        log.info("");

        // Get system properties
        Properties properties = new Properties();

        // Setup mail server host
        properties.setProperty("mail.smtp.host", mailHost);
        log.info("Mail host: " + mailHost);

        // Setup mail server port
        properties.setProperty("mail.smtp.port", mailPort);
        properties.setProperty("mail.smtp.socketFactory.port", mailPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "true");
        log.info("Mail port: " + mailPort);

        // enable auth
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        log.info("Auth: " + "true");

        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("username", "password");
            }
        });

        log.info("System properties set, properties added to a new session");

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(mailFrom));
            log.info("From: " + mailFrom);

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            log.info("To: " + mailTo);

            // Set CC: header field of the header.
            for (int i = 0; i < mailToCc.split(",").length; i++) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(mailToCc.split(",")[i]));
                log.info("CC" + i + ": " + mailToCc.split(",")[i]);
            }

            // Set Subject: header field
            message.setSubject(mailSubject);
            log.info("Subject: " + mailSubject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText("\n\n\n" + mailContent + "\n\n\n");
            log.info("Content: " + mailContent);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            for (int i = 0; i < a.length; i++) {
                if (a[i].getName().contains(extension)) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource csvAttatchment = new FileDataSource(a[i]);
                    messageBodyPart.setDataHandler(new DataHandler(csvAttatchment));
                    messageBodyPart.setFileName(("NC Bank transactions " + a[i].getName()));
                    multipart.addBodyPart(messageBodyPart);
                    log.info("Attachment csv source: " + a[i]);
                }
            }

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            log.info(" Sent email successfully.");
            log.info("");
            mailSentSuccessfully = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            log.error(mex);
            log.info("");
        }
        return mailSentSuccessfully;
    }
}