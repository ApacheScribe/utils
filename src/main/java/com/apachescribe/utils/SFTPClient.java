package com.apachescribe.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// @Component
public class SFTPClient {

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
            System.out.println(" ------------------- Begin SFTP session");
            session = jsch.getSession(username, host, Integer.parseInt(port));
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("Session established");
        } catch (Exception e) {
            System.out.println("SFTP Session creation failed with error: " + e);
        }
    }

    public void upload(String src, String dest) {
        try {
            connect();
            System.out.println("Creating sftp upload channel ... ");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.put(src, dest);
            System.out.println("Upload for " + src + " successful");
            sftpChannel.exit();
            System.out.println(" ------------------- End SFTP session");
            ;
        } catch (Exception e) {
            System.err.println(" ... Sftp channel error: " + e);
            System.exit(500);
        }
    }

    public void download(String src, String dest) {
        try {
            System.out.println("Creating sftp download channel");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            System.out.println("Downloading file ... ");
            sftpChannel.get(src, dest);
            sftpChannel.exit();
            System.out.println("Download successful. Sftp upload channel closed");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void disconnect() {
        try {
            if (session != null) {
                System.out.println("Disconnecting session");
                session.disconnect();
                System.out.println("Session disconnected");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}