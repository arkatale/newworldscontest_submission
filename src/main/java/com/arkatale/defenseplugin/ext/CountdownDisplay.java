package com.arkatale.defenseplugin.ext;

import com.arkatale.defenseplugin.logic.DefendSession;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CountdownDisplay {
    private final DefendSession defendSession;
    private World world;

    public CountdownDisplay(DefendSession defendSession){
        this.defendSession = defendSession;
    }

    public void updateCountdown(int seconds) {
        // Update the countdown display with the remaining seconds
//        Universe.get().sendMessage(Message.raw(String.valueOf(seconds)));
        defendSession.startingPlayer.sendMessage(Message.raw(String.valueOf(seconds)));
    }

    public void startCountdown(int startSeconds, World world) {
        this.world = world;

        AtomicInteger timer = new AtomicInteger(startSeconds);

        // Wir erstellen einen Loop, der sich selbst wiederholt
        schedulerLoop(timer);
    }

    private void schedulerLoop(AtomicInteger timer) {
        if (timer.get() <= 0) {
//            Universe.get().sendMessage(Message.raw("Wave starts NOW!"));
            world.sendMessage(Message.raw("Wave starts NOW!"));
            return;
        }

        CompletableFuture.runAsync(() -> {
            world.execute(() -> {
                int remaining = timer.decrementAndGet();

                //Nur alle 5s und letzte 5s dann spammen
                if (remaining % 5 == 0 || remaining <= 5) {
//                    Universe.get().sendMessage(Message.raw("Time left: " + remaining));
                    world.sendMessage(Message.raw("Time left: " + remaining));
                }

//              erst wenn 1mal fertig dann nochmal
                schedulerLoop(timer);
            });
        }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
    }
}
