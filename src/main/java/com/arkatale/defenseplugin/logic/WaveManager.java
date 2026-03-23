package com.arkatale.defenseplugin.logic;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.sun.tools.jconsole.JConsoleContext;
import it.unimi.dsi.fastutil.Pair;
//import sun.awt.windows.WChoicePeer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WaveManager {
    private int currentWave = 0;
    private GameState gameState;
    private World world;
    private EntityStore store;
    private final Vector3i toDefendPosition;
    private int countdownSeconds;
    private ArrayList<Pair<Ref<EntityStore>, INonPlayerCharacter>> spawnedNPCsThisWave;


    public WaveManager(Vector3i toDefendPosition, World world, EntityStore store) {
        this.toDefendPosition = toDefendPosition;
        this.world = world;
        this.store = store;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public boolean startWaves() {
//        if(spawnedNPCsThisWave ==  null){
            spawnedNPCsThisWave = new ArrayList<>();
//        }

        if (gameState != null) {
//            this.store = world.getEntityStore();
            gameState = GameState.COUNTDOWN;
            return true;
        }

        world.execute(
                () -> {
                    CompletableFuture.runAsync(() ->
                            {
//                                countdownSeconds = 1;
                                startCountdown(1);
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

    public void spawnWave() {
        var basePos = toDefendPosition.clone().add(15, 0, 0);
        for (int i = 0; i < 1; i++) {
//            world.spawnEntity()
            //TODO
            //position and rotation
            var position = basePos.clone().add(0, 0, i).toVector3d();
            var rotation = Vector3f.lookAt(toDefendPosition.toVector3d());
            //todo find ground position and avoid spawning in ground
            world.execute(
                    () -> {
                        Pair<Ref<EntityStore>, INonPlayerCharacter> result = NPCPlugin.get().spawnNPC(store.getStore(), "Zombie_Aberrant", null, position, rotation);
                        if (result != null) {
                            spawnedNPCsThisWave.add(result);
                            Ref<EntityStore> npcRef = result.first();
                            INonPlayerCharacter npc = result.second();

                            // Proceed with customization...
//                setupNPCInventory(npcRef, store);
                        }

                    }
            );

        }

//how to make this good?
        world.execute(() -> {
            CompletableFuture.runAsync(() ->
                    {
                        for (Pair<Ref<EntityStore>, INonPlayerCharacter> pair : spawnedNPCsThisWave) {

                            var test = store.getStore().removeEntity(pair.key(), RemoveReason.UNLOAD);
                            HytaleLogger.getLogger().atWarning().log(test.toString());
                            spawnedNPCsThisWave.remove(pair);

                        }
                    }
                    , CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS));

        });

    }

    public int getCountdownSeconds() {
        return countdownSeconds;
    }

    public void startCountdown(int countdownSeconds) {
        this.countdownSeconds = countdownSeconds;
    }
}