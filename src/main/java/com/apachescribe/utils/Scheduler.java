package com.apachescribe.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// @Component
public class Scheduler {

    // @Value("${sendTransactionFileTimeIntervalInSeconds}")

    // Here, Object is the return type of the function that is accepted as an
    // argument for this

    // change void to whatever you need
    public static void timeoutFunction(String fnReturnVal) {

        // Object p = null; // whatever object you need here

        String threadSleeptime = null;

        Config config;

        try {
            config = ConfigReader.getConfigProperties();
            threadSleeptime = config.getThreadSleepTime();

        } catch (Exception e) {
            threadSleeptime = "100000";
            System.err.println(e);
            System.out.println("");
            System.err.println("Defaulting thread sleep time to " + threadSleeptime + " miliseconds.");
            System.out.println("");
        }

        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<Object> task = new Callable<Object>() {
            public Object call() {
                // Do job here using --- fnReturnVal --- and return appropriate value
                return null;
            }
        };
        Future<Object> future = executor.submit(task);

        try {
            // p = future.get(Integer.parseInt(threadSleeptime), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.err.println(e + ". The function timed out after [" + threadSleeptime
                    + "] miliseconds before a response was received.");
        } finally {
            // if task has started then don't stop it
            future.cancel(false);
        }
    }

    private static String returnString() {
        return "hello";
    }

    public static void main(String[] args) {
        timeoutFunction(returnString());
    }

    public void scheduleInterval(Integer intervalInSeconds) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                Logic.run();
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = intervalInSeconds * 1000;

        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);

    }
}

class Logic {

    public static void run() {
    }

}

class Payment {
}
