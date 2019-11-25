/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apachescribe.utils;

import java.io.*;

import org.apache.log4j.Logger;

public class PGP {

    private static final Logger log = Logger.getLogger(PGP.class);

    public static String Decrypt(String file) {
        log.info("file to Decrypt: " + file);
        String outfile = file.substring(0, file.length() - 4);
        try {
            String cmd = "gpg -o " + outfile + " --passphrase " + "LoadConfigs.PASSPHRASE"
                    + " --batch --yes --no-use-agent -d " + file;
            Process p = Runtime.getRuntime().exec(cmd);
            p.getInputStream();
            p.waitFor();
            p.exitValue();
            p.destroy();
            log.info("finished Decrypting- Decrypted file: " + outfile);
        } catch (IOException | InterruptedException ex) {
            outfile = "error" + ex.getMessage();
            log.error("error: " + ex.getMessage());

        }
        return outfile;
    }

    public static String Encrypt(String file) {
        log.info("file to Encrypt: " + file);
        String outfile = file + ".gpg";

        try {
            String cmd = "gpg -r " + "LoadConfigs.E_KEYID" + " --always-trust -o " + outfile + " -e " + file;
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            p.exitValue();
            p.destroy();
            log.info("finished encrypting- encrypted file: " + outfile);
        } catch (IOException | InterruptedException ex) {
            outfile = "error" + ex.getMessage();
            log.error("error: " + ex.getMessage());
        }
        return outfile;
    }

    // public static void main(String args[]) throws Exception {
    // String file = "/home/system/test/testone1.txt";
    // Encrypt(file);
    //
    // }
}
