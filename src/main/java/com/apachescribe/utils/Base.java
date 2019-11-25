/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.apachescribe.utils;

import java.math.BigInteger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Base {

    private static final byte[] base64ToInt = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1,
            -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
    private static final byte[] altBase64ToInt = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1, 62, 9, 10,
            11, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, 18, 19, 21, 20, 26, 27, 28,
            29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 22, 23, 24,
            25 };

    public static String base64ToByteArray(String s, boolean flag) {
        byte[] abyte0 = flag ? altBase64ToInt : base64ToInt;
        int i = s.length();
        int j = i / 4;
        if (4 * j != i) {
            throw new IllegalArgumentException("String length must be a multiple of four.");
        }
        int k = 0;
        int l = j;
        if (i != 0) {
            if (s.charAt(i - 1) == '=') {
                k++;
                l--;
            }
            if (s.charAt(i - 2) == '=') {
                k++;
            }
        }
        byte[] abyte1 = new byte[3 * j - k];
        int i1 = 0;
        int j1 = 0;
        for (int k1 = 0; k1 < l; k1++) {
            int i2 = base64toInt(s.charAt(i1++), abyte0);
            int k2 = base64toInt(s.charAt(i1++), abyte0);
            int i3 = base64toInt(s.charAt(i1++), abyte0);
            int j3 = base64toInt(s.charAt(i1++), abyte0);
            abyte1[(j1++)] = ((byte) (i2 << 2 | k2 >> 4));
            abyte1[(j1++)] = ((byte) (k2 << 4 | i3 >> 2));
            abyte1[(j1++)] = ((byte) (i3 << 6 | j3));
        }
        if (k != 0) {
            int l1 = base64toInt(s.charAt(i1++), abyte0);
            int j2 = base64toInt(s.charAt(i1++), abyte0);
            abyte1[(j1++)] = ((byte) (l1 << 2 | j2 >> 4));
            if (k == 1) {
                int l2 = base64toInt(s.charAt(i1++), abyte0);
                abyte1[(j1++)] = ((byte) (j2 << 4 | l2 >> 2));
            }
        }
        return new String(abyte1);
    }

    private static int base64toInt(char c, byte[] abyte0) {
        byte byte0 = abyte0[c];
        if (byte0 < 0) {
            throw new IllegalArgumentException("Illegal character " + c);
        }
        return byte0;
    }

    public static String decrypt(String secret) throws Exception {
        String add = "amFhcyBpcyB0aGUgd2F5";
        add = base64ToByteArray(add, false);
        byte[] kbytes = add.getBytes();
        SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
        BigInteger n = new BigInteger(secret, 16);
        byte[] encoding = n.toByteArray();
        if (encoding.length % 8 != 0) {
            int length = encoding.length;
            int newLength = (length / 8 + 1) * 8;
            int pad = newLength - length;
            byte[] old = encoding;
            encoding = new byte[newLength];
            for (int i = old.length - 1; i >= 0; i--) {
                encoding[(i + pad)] = old[i];
            }
        }
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(2, key);
        byte[] decode = cipher.doFinal(encoding);
        return new String(decode);
    }
    // 5f78dc15b9a559cb5e408c5212b5f4fb
    // public static void main(String args[]) throws Exception {
    // //LoadConfig.readAndLockConfigFile();
    // String creds = decrypt("-ddabbc721b13460f2b5eaef168a6724");
    // // String creds = encrypt("-6f815e10eaa495c2"); NLSDRP123*
    // System.out.println("cred "+ creds);
    // }
}
