package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import it.unimi.dsi.fastutil.Pair;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WaveManager {
    private int currentWave = 0;
    private GameState gameState;
    private World world;
    private EntityStore store;
    private final Vector3i toDefendPosition;

    public WaveManager(Vector3i toDefendPosition) {
        this.toDefendPosition = toDefendPosition;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public boolean startWaves(Vector3i pos, World world) {
        if (gameState == null) {
            this.world = world;
            this.store = world.getEntityStore();
            gameState = GameState.COUNTDOWN;
            return true;
        }
        world.execute(
                () -> {
                    CompletableFuture.runAsync(() ->
                            {
                spawnWave();
                            }
                            , CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
                }
        );
        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void spawnWave(){
        for (int i = 0; i < 10; i++){
//            world.spawnEntity()
            Pair<Ref<EntityStore>, INonPlayerCharacter> result = NPCPlugin.get().spawnNPC(store, "Kweebec_Sapling", null, position, rotation);
        }
    }
}