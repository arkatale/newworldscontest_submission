package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.math.vector.Vector3i;

public class DefendSession {
    private final Vector3i defendPosition;
    private final WaveManager waveManager;

    public DefendSession(Vector3i defendPosition, WaveManager waveManager){
        this.defendPosition = defendPosition;
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
