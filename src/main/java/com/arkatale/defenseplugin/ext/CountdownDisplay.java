package com.arkatale.defenseplugin.ext;

import com.arkatale.defenseplugin.logic.DefendSession;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.Universe;

public class CountdownDisplay {
    private final DefendSession defendSession;

    public CountdownDisplay(DefendSession defendSession){
        this.defendSession = defendSession;
    }

    public void updateCountdown(int seconds) {
        // Update the countdown display with the remaining seconds
//        Universe.get().sendMessage(Message.raw(String.valueOf(seconds)));
        defendSession.startingPlayer.sendMessage(Message.raw(String.valueOf(seconds)));
    }
}
