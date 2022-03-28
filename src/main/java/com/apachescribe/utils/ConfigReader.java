package com.apachescribe.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    // Loading the properties config file
    public static Config getConfigProperties() throws Exception {

        Config config = new Config();

        Properties prop = new Properties();
        // load a properties file from class path, inside static method
        InputStream input = new FileInputStream("/home/system/apps/java/Config/config.properties");

        try {
            prop.load(input);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // get and set the property values
        config.setPostTransactionThreadSleepTime(prop.getProperty("postTransactionThreadSleepTime"));
        config.setRepushCount(prop.getProperty("repushCount"));
        config.setLoopStrLen(prop.getProperty("loopStrLen"));
        config.setLoopSubStrBeginWith(prop.getProperty("loopSubStrBeginWith"));
        return config;
    }
}

class Config {

    private String postTransactionThreadSleepTime;
    private String repushCount;
    private String loopSubStrBeginWith;
    private String loopStrLen;

    public String getThreadSleepTime() {
        return this.postTransactionThreadSleepTime;
    }

    public void setPostTransactionThreadSleepTime(String postTransactionThreadSleepTime) {
        this.postTransactionThreadSleepTime = postTransactionThreadSleepTime;
    }

    public String getRepushCount() {
        return this.repushCount;
    }

    public void setRepushCount(String repushCount) {
        this.repushCount = repushCount;
    }

    public String getLoopSubStrBeginWith() {
        return this.loopSubStrBeginWith;
    }

    public void setLoopSubStrBeginWith(String loopSubStrBeginWith) {
        this.loopSubStrBeginWith = loopSubStrBeginWith;
    }

    public String getLoopStrLen() {
        return this.loopStrLen;
    }

    public void setLoopStrLen(String loopStrLen) {
        this.loopStrLen = loopStrLen;
    }

    @Override
    public String toString() {
        return "{" + " postTransactionThreadSleepTime='" + getThreadSleepTime() + "'" + ", repushCount='"
                + getRepushCount() + "'" + ", loopSubStrBeginWith='" + getLoopSubStrBeginWith() + "'" + ", loopStrLen='"
                + getLoopStrLen() + "'" + "}";
    }
}