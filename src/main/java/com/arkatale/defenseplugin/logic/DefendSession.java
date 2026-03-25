package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.entity.entities.Player;

public class DefendSession {
    private final Vector3i defendPosition;
    public final Player startingPlayer;
    private final WaveManager waveManager;

    public DefendSession(Vector3i defendPosition, Player startingPlayer, WaveManager waveManager){
        this.defendPosition = defendPosition;
        this.startingPlayer = startingPlayer;
        this.waveManager = waveManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }


//    public static DefendSession checkAndStartDefendSession(Vector3i defendPosition) {
//
//    }

//    public void add(WaveManager waveManager) {
//    }
}
