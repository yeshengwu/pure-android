package com.example.mylibrary.testconcurrcybook;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * OutOfTime
 * <p/>
 * Class illustrating confusing Timer behavior
 *
 * @author Brian Goetz and Tim Peierls
 *
 * https://jcip.net/listings/OutOfTime.java
 */
public class OutOfTime {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1); // java.lang.IllegalStateException: Timer already cancelled
        SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {
        public void run() {
            throw new RuntimeException();
        }
    }
}
