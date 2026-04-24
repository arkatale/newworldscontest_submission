package com.arkatale.defenseplugin.ext;

import com.arkatale.defenseplugin.logic.DefendSession;
import com.hypixel.hytale.protocol.ItemWithAllMetadata;
import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolShowAnchor;
import com.hypixel.hytale.protocol.packets.interface_.EditorBlocksChange;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import com.hypixel.hytale.server.core.util.NotificationUtil;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CountdownDisplay {
    private final DefendSession defendSession;
    private World world;
    private PlayerRef playerRef;

    public CountdownDisplay(DefendSession defendSession) {
        this.defendSession = defendSession;
    }

    public void updateCountdown(int seconds) {
        // Update the countdown display with the remaining seconds
//        Universe.get().sendMessage(Message.raw(String.valueOf(seconds)));
        defendSession.startingPlayer.sendMessage(Message.raw(String.valueOf(seconds)));
    }

    public CompletableFuture<Void> startCountdown(int startSeconds, PlayerRef playerRef, boolean isCountdown) {
        this.world = Universe.get().getWorld(playerRef.getWorldUuid());
        this.playerRef = playerRef;
        AtomicInteger timer = new AtomicInteger(startSeconds);

        CompletableFuture<Void> onFinished = new CompletableFuture<>();
        schedulerLoop(timer, isCountdown, onFinished, true);

        return onFinished; // Wir geben das Versprechen zurück, dass wir fertig werden
    }

    private void schedulerLoop(AtomicInteger timer, boolean isCountdown, CompletableFuture<Void> onFinished, boolean firstExec) {
        if (timer.get() <= 0 ) {
            if (isCountdown) {

            } else {
                world.sendMessage(Message.raw("Wave {$waveNumber} ended!"));
            }
            onFinished.complete(null);
            return;
        }

        var time = 1;
        if(firstExec) time = 0;

        CompletableFuture.runAsync(() -> {
            world.execute(() -> {

                int remaining = timer.decrementAndGet();

                //Nur alle 5s und letzte 5s dann spammen
                if (remaining % 5 == 0 || remaining <= 5) {


                    if (isCountdown) {
                        EventTitleUtil.showEventTitleToPlayer(playerRef, Message.raw(String.valueOf(remaining + 1)), Message.raw("Wave ${nextWaveNum} starts in"), false, (String)null, 1.2F, 0F, 0F);

                        world.sendMessage(Message.raw("Countdown: " + remaining));
                    } else {
                        //                    Universe.get().sendMessage(Message.raw("Time left: " + remaining));
                        world.sendMessage(Message.raw("Time left: " + remaining));
//                        https://hytalemodding.dev/en/docs/guides/plugin/send-notifications

//                        var playerRef = Universe.get().getPlayer(player.getUuid());
                        var packetHandler = playerRef.getPacketHandler();
                        var primaryMessage = Message.raw("THIS WORKS!!!").color("#00FF00");
                        var secondaryMessage = Message.raw("This is the secondary message").color("#228B22");
                        var icon = new ItemStack("Weapon_Sword_Mithril", 1).toPacket();
                        NotificationUtil.sendNotification(
                                packetHandler,
                                primaryMessage,
                                secondaryMessage,
                                (ItemWithAllMetadata) icon);
                    }
                }

                schedulerLoop(timer, isCountdown, onFinished, false);
            });
        }, CompletableFuture.delayedExecutor(time, TimeUnit.SECONDS));
    }
}