package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3f;
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

    public WaveManager(Vector3i toDefendPosition, World world) {
        this.toDefendPosition = toDefendPosition;
        this.world = world;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public boolean startWaves() {
        if (gameState != null) {
            this.store = world.getEntityStore();
            gameState = GameState.COUNTDOWN;
            return true;
        }

        world.execute(
                () -> {
                    CompletableFuture.runAsync(() ->
                            {
                spawnWave(); //todo anzeige wie lange noch countdown und Möglichkeit zu überspringen und gleich anfangen
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
        var basePos = toDefendPosition.clone().add(15, 0,0);
        for (int i = -10; i < 10; i++){
//            world.spawnEntity()
            //TODO
            //position and rotation
            var pos = basePos.clone().add(0,0,i).toVector3d();
            var rotation = Vector3f.lookAt(toDefendPosition.toVector3d());
            //todo find ground position and avoid spawning in ground
            world.execute(
                    () -> {
            Pair<Ref<EntityStore>, INonPlayerCharacter> result =
                NPCPlugin.get().spawnNPC(store.getStore(), "Kweebec_Sapling", null, pos, rotation);
            if (result != null) {
                Ref<EntityStore> npcRef = result.first();
                INonPlayerCharacter npc = result.second();

                // Proceed with customization...
//                setupNPCInventory(npcRef, store);
            }

                    }
            );

        }
    }
}