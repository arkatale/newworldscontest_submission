package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class DefendSession {
    private final Vector3i defendPosition;
    public final Player startingPlayer;
    private final WaveManager waveManager;

    public DefendSession(Vector3i defendPos, Player startingPlayer, Vector3i defendPosition, World world, EntityStore store, PatrolPathMarkerEntity target) {
        this.defendPosition = defendPosition;
        this.startingPlayer = startingPlayer;
        this.waveManager = new WaveManager(defendPos, world, store, target, this);
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
