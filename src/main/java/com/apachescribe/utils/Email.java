package com.apachescribe.utils;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
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

    // @Value("${mail-to-cc1}")
    private String mailToCc1;

    // @Value("${mail-to-cc2}")
    private String mailToCc2;

    // @Value("${mail-to-cc3}")
    private String mailToCc3;

    // @Value("${mail-to-cc4}")
    private String mailToCc4;

    // @Value("${mail-subject}")
    private String mailSubject;

    // @Value("${mail-content}")
    private String mailContent;

    // @Value("${mail-host}")
    private String mailHost;

    // @Value("${mail-port}")
    private String mailPort;

    public void sendMailWithAttachment(File attatchment) {

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server host
        properties.setProperty("mail.smtp.host", mailHost);
        log.info("------------------- Begin mail sending");
        log.info("Mail host: " + properties.getProperty("mail.smtp.host"));

        // Setup mail server port
        properties.setProperty("mail.smtp.port", mailPort);
        log.info("Mail port: " + mailPort);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

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
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(mailToCc1));
            log.info("CC1: " + mailToCc1);

            // Set CC: header field of the header.
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(mailToCc2));
            log.info("CC2: " + mailToCc2);

            // Set CC: header field of the header.
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(mailToCc3));
            log.info("CC3: " + mailToCc3);

            // Set CC: header field of the header.
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(mailToCc4));
            log.info("CC4: " + mailToCc4);

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

            // Part two is attachment1
            messageBodyPart = new MimeBodyPart();
            DataSource csvAttatchment = new FileDataSource(attatchment.getAbsolutePath());
            messageBodyPart.setDataHandler(new DataHandler(csvAttatchment));
            messageBodyPart.setFileName(attatchment.getName());
            log.info("Attachment csv source: " + attatchment);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            log.info("------------------- Mail sent successfully");
        } catch (MessagingException mex) {
            log.error(mex);
            mex.printStackTrace();
            System.exit(500);
        }
    }
}