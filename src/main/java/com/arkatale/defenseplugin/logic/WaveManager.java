package com.arkatale.defenseplugin.logic;

import com.arkatale.defenseplugin.components.AttackerComponent;
import com.arkatale.defenseplugin.ext.CountdownDisplay;
import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.util.InventoryHelper;
import it.unimi.dsi.fastutil.Pair;
//import sun.awt.windows.WChoicePeer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WaveManager {
    private final Pair<Ref<EntityStore>, INonPlayerCharacter> target;
    private final DefendSession defendSession;
    private int currentWave = 0;
    private GameState gameState;
    private World world;
    private final Store<EntityStore> store;
    private final Vector3i toDefendPosition;
    private int countdownSeconds;
    private ArrayList<Pair<Ref<EntityStore>, INonPlayerCharacter>> spawnedNPCsThisWave;
    CountdownDisplay countdownDisplay;

    public WaveManager(Vector3i toDefendPosition, World world, EntityStore entityStore, Pair<Ref<EntityStore>, INonPlayerCharacter> target, DefendSession defendSession) {
        this.toDefendPosition = toDefendPosition;
        this.world = world;
        this.store = entityStore.getStore();
        this.target = target;
        this.defendSession = defendSession;
        countdownDisplay = new CountdownDisplay(defendSession);
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
                                 //TODO morgen
                                //in größerem umkreis spawnen und mehrere und dann bis ende von welle
                                //erst bei ende von welle despawn . vlt mit dem timer in npc drin

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
        for (int i = 0; i < 9; i++) {
//            world.spawnEntity()
            //TODO
            //position and rotation
            var position = basePos.clone().add(i, 0, 0).toVector3d();
            var rotation = Vector3f.lookAt(toDefendPosition.toVector3d());
            //todo find ground position and avoid spawning in ground
            world.execute(
                    () -> {
                        Pair<Ref<EntityStore>, INonPlayerCharacter> result = NPCPlugin.get().spawnNPC(store, "NexusAttacker_Goblin", null, position, rotation);
                        if (result != null) {
                            spawnedNPCsThisWave.add(result);
                            Ref<EntityStore> npcRef = result.first();
                            INonPlayerCharacter npc = result.second();

var attackerComponent = store.getComponent(npcRef, AttackerComponent.getComponentType());

if (attackerComponent == null){
//    store.addComponent(npcRef, AttackerComponent.getComponentType());
//    ^^[2026/04/02 13:28:19 SEVERE]                    [Hytale] Exception in thread Thread[#98,WorldThread - default,5,InnocuousForkJoinWorkerThreadGroup]:
//        java.lang.IllegalStateException: Invalid component at index 6 expected class com.arkatale.defenseplugin.components.AttackerComponent but found null
//        at com.hypixel.hytale.component.Archetype.validateComponents(Archetype.java:173)
//    store.ensureComponent(npcRef, AttackerComponent.getComponentType());
}

                            // Proceed with customization...
                setupNPCInventory(npcRef, store);

//                store.getComponent(npcRef, Targetcompo)
//                            npc.
                        }

                    }
            );

        }
        var removeAfterSeconds = 8;
        removeNPCs(removeAfterSeconds);

    }

    private void setupNPCInventory(Ref<EntityStore> npcRef, Store<EntityStore> store) {
        NPCEntity npcComponent = store.getComponent(npcRef, Objects.requireNonNull(NPCEntity.getComponentType()));

// Initialize inventory size (e.g., 3 rows, 9 columns, 0 offset)
//        npcComponent.setInventorySize(3, 9, 0);

        Inventory inventory = npcComponent.getInventory();

// Add a Thorium Mace to the first slot of the hotbar
        inventory.getHotbar().addItemStackToSlot((short) 0, new ItemStack("Weapon_Mace_Thorium", 1));

// Equip a Thorium Helmet using the InventoryHelper
        InventoryHelper.useArmor(inventory.getArmor(), "Armor_Thorium_Head");

        Ref<EntityStore> target1 = target.key();
        npcComponent.onFlockSetTarget("LockedTarget", target1);
//        npcComponent.
    }

    private void removeNPCs(int removeAfterSeconds) {
        //how to make this good?
//        world.execute(() -> {
        CompletableFuture.runAsync(() -> {
            world.execute(() -> {
                for (var pair : spawnedNPCsThisWave) {
                    var entityRef = pair.key();
                    try {
                        if (entityRef.isValid()) {
                            var result = store.removeEntity(entityRef, RemoveReason.REMOVE);
                            HytaleLogger.getLogger().atInfo().log("Entity removed: " + result);
                        } else {
                            HytaleLogger.getLogger().atWarning().log("Entity was not valid, skipping remove");
                        }
                    } catch (Exception e) {
                        HytaleLogger.getLogger().atSevere().log("Exception during removeEntity", e);
                    }
                }
                spawnedNPCsThisWave.clear(); // Liste nach Abarbeitung leeren
            });
        }, CompletableFuture.delayedExecutor(removeAfterSeconds, TimeUnit.SECONDS));

//        });
    }

    public int getCountdownSeconds() {
        return countdownSeconds;
    }

    public void startCountdown(int countdownSeconds) {
        this.countdownSeconds = countdownSeconds;

        for(int i = countdownSeconds; i >= 0; i--) {
            final int secondsLeft = i;
            world.execute(() -> {
                CompletableFuture.runAsync(() -> {

                }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
            });


        }
    }
}