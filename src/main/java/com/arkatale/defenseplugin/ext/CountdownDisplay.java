package com.arkatale.defenseplugin.ext;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.Universe;

public class CountdownDisplay {
    public void updateCountdown(int seconds) {
        // Update the countdown display with the remaining seconds
        Universe.get().sendMessage(Message.raw(String.valueOf(seconds)));
    }
}
