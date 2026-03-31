package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.Pair;

public class DefendSession {
    private final Vector3i defendPosition;
    public final Player startingPlayer;
    private final WaveManager waveManager;

    public DefendSession(Vector3i defendPos, Player startingPlayer, Vector3i defendPosition, World world, EntityStore store, Pair<Ref<EntityStore>, INonPlayerCharacter> target) {
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
