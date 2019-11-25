package com.apachescribe.utils;

import java.util.Timer;
import java.util.TimerTask;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// @Component
public class Scheduler {

    // @Value("${sendTransactionFileTimeIntervalInSeconds}")
    private Integer intervalInSeconds;

    public void schedule() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                Logic.run();
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = this.intervalInSeconds * 1000;

        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);

    } // end of main
}

class Logic {

    public static void run() {
    }

}