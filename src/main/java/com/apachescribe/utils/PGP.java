/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apachescribe.utils;

import java.io.*;

public class PGP {

    public static String Decrypt(String file) {
        System.out.println("file to Decrypt: " + file);
        String outfile = file.substring(0, file.length() - 4);
        try {
            String cmd = "gpg -o " + outfile + " --passphrase " + "LoadConfigs.PASSPHRASE"
                    + " --batch --yes --no-use-agent -d " + file;
            Process p = Runtime.getRuntime().exec(cmd);
            p.getInputStream();
            p.waitFor();
            p.exitValue();
            p.destroy();
            System.out.println("finished Decrypting- Decrypted file: " + outfile);
        } catch (IOException | InterruptedException ex) {
            outfile = "error" + ex.getMessage();
            System.err.println("error: " + ex.getMessage());

        }
        return outfile;
    }

    public static String Encrypt(String file) {
        System.out.println("file to Encrypt: " + file);
        String outfile = file + ".gpg";

        try {
            String cmd = "gpg -r " + "LoadConfigs.E_KEYID" + " --always-trust -o " + outfile + " -e " + file;
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            p.exitValue();
            p.destroy();
            System.out.println("finished encrypting- encrypted file: " + outfile);
        } catch (IOException | InterruptedException ex) {
            outfile = "error" + ex.getMessage();
            System.err.println("error: " + ex.getMessage());
        }
        return outfile;
    }

    // public static void main(String args[]) throws Exception {
    // String file = "/home/system/test/testone1.txt";
    // Encrypt(file);
    //
    // }
}
