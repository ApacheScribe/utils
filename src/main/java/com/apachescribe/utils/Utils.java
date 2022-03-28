package com.apachescribe.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

// import org.springframework.stereotype.Component;

public class Utils {

    public static String filenameTimestamp(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dateString = format.format(date) + "_C2B.CSV";
        System.out.println("Filename will be: " + dateString);
        return dateString;
    }

    public static String genUniqRef() {
        // Random rng, String characters, int length
        // Uniq Sample: NW1704032220GH
        Random rng = new Random();
        String ref;
        String reference;
        String code = "BANC";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String yDate = Time.today().substring(2);
        // String yTime = Time.getCurrentTimeStamp();
        int length = 2;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));

        }
        ref = new String(text);
        reference = code + yDate + ref;
        return reference;
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 2) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}