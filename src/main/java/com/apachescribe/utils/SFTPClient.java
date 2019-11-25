package com.apachescribe.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.apache.log4j.Logger;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// @Component
public class SFTPClient {

    private static final Logger log = Logger.getLogger(SFTPClient.class);

    // @Value("${sftp-host}")
    private String host;

    // @Value("${sftp-port}")
    private String port;

    // @Value("${sftp-username}")
    private String username;

    // @Value("${sftp-password}")
    private String password;

    // @Value("${private-key-path}")
    // private String privateKey;

    private Session session = null;

    public void connect() {
        JSch jsch = new JSch();
        try {
            // if (privateKey != "none") {
            // jsch.addIdentity("private-key-path");
            // }
            log.info(" ------------------- Begin SFTP session");
            session = jsch.getSession(username, host, Integer.parseInt(port));
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            log.info("Session established");
        } catch (Exception e) {
            log.error("SFTP Session creation failed with error: " + e);
        }
    }

    public void upload(String src, String dest) {
        try {
            connect();
            log.info("Creating sftp upload channel ... ");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.put(src, dest);
            log.info("Upload for " + src + " successful");
            sftpChannel.exit();
            log.info(" ------------------- End SFTP session");
            ;
        } catch (Exception e) {
            log.error(" ... Sftp channel error: " + e);
            System.exit(500);
        }
    }

    public void download(String src, String dest) {
        try {
            log.info("Creating sftp download channel");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            log.info("Downloading file ... ");
            sftpChannel.get(src, dest);
            sftpChannel.exit();
            log.info("Download successful. Sftp upload channel closed");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void disconnect() {
        try {
            if (session != null) {
                log.info("Disconnecting session");
                session.disconnect();
                log.info("Session disconnected");
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

}