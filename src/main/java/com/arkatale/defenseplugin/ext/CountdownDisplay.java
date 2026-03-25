package com.arkatale.defenseplugin.ext;

public class CountdownDisplay {
    public void updateCountdown(int seconds) {
        // Update the countdown display with the remaining seconds
        System.out.println("Countdown: " + seconds + " seconds remaining");
        HytaleLogger    .log("Countdown updated: " + seconds + " seconds remaining");

    }
}
