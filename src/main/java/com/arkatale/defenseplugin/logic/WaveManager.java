package com.arkatale.defenseplugin.logic;

//import ch.randelshofer.fastdoubleparser.bte.ByteToIntMap;
import com.arkatale.defenseplugin.components.AttackerComponent;
import com.arkatale.defenseplugin.ext.CountdownDisplay;
import com.google.crypto.tink.subtle.Random;
import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import it.unimi.dsi.fastutil.Pair;
//import sun.awt.windows.WChoicePeer;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WaveManager {
    private final PatrolPathMarkerEntity patrolPathMarkerEntity;
    private final DefendSession defendSession;
    private int currentWave = 0;
    private GameState gameState;
    private World world;
    private final Store<EntityStore> store;
    private final Vector3i toDefendPosition;
    private int countdownSeconds;
    private ArrayList<Pair<Ref<EntityStore>, INonPlayerCharacter>> spawnedNPCsThisWave;
    CountdownDisplay countdownDisplay;
    private int corruption = 0;
//    private ByteToIntMap patrolPathMarkers;
//private final ArrayList<PatrolPathMarkerEntity> patrolPathMarkers = new ArrayList<PatrolPathMarkerEntity>();
//    PatrolPathMarkerEntity[] mutablePatrolPathMarkers = {null};

    public WaveManager(Vector3i toDefendPosition, World world, EntityStore entityStore, PatrolPathMarkerEntity patrolPathMarkerEntity, DefendSession defendSession) {
        this.toDefendPosition = toDefendPosition;
        this.world = world;
        this.store = entityStore.getStore();
        this.patrolPathMarkerEntity = patrolPathMarkerEntity;
        this.defendSession = defendSession;
        countdownDisplay = new CountdownDisplay(defendSession);
    }

    public void cleanup(){
        store.removeEntity(patrolPathMarkerEntity.getReference(), RemoveReason.REMOVE);
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
        var playerRef = Universe.get().getPlayers().get(0);
        countdownDisplay.startCountdown(3, playerRef, true);
        world.execute(
                () -> {
                    CompletableFuture.runAsync(() ->
                            {
//                                countdownSeconds = 1;
//                                 startCountdown(1);
                                 //TODO morgen
                                //in größerem umkreis spawnen und mehrere und dann bis ende von welle
                                //erst bei ende von welle despawn . vlt mit dem timer in npc drin

                                spawnWave(); //todo anzeige wie lange noch countdown und Möglichkeit zu überspringen und gleich anfangen
                            }
                            , CompletableFuture.delayedExecutor(10, TimeUnit.SECONDS));
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
        var playerRef = Universe.get().getPlayers().get(0);
        var ref = playerRef.getReference();

        //gehört hier nicht hin, aber ...
        String pathName = "ArkaTale_CoreSiege";
        Double pauseTime = (double)0.0F;
        Float obsvAngle = 0f;
        UUID uuid = UUID.randomUUID();
//        Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
//        var entity = ATPrefabPathHelper.addMarker(store, ref, uuid, pathName, pauseTime, obsvAngle, (short)-1, 0);
        world.execute(
                () -> {
//                    var entity = ATPrefabPathHelper.addMarker(store, ref, uuid, pathName, pauseTime, obsvAngle, (short)-1, 0);
//                    patrolPathMarkers.put(0, entity);
//                    mutablePatrolPathMarkers[0] = entity;
//                    store.removeEntity(entity.getReference(), RemoveReason.REMOVE);
                }
        );
//        BuilderToolsPlugin.getState(playerComponent, playerRef).setActivePrefabPath(uuid);

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
                        var random = Random.randInt(2);
                        var npcTypeToSpawn = "NexusAttacker_Skeleton_Archer";
                        if(random > 0){
                            npcTypeToSpawn = "NexusAttacker_Goblin";
                        }
                        Pair<Ref<EntityStore>, INonPlayerCharacter> result = NPCPlugin.get().spawnNPC(store, npcTypeToSpawn, null, position, rotation);
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
//        var waveTime = 30;
        AtomicInteger waveTime = new AtomicInteger(30);
        CompletableFuture.runAsync(() -> {
            world.execute(() -> {
                int current = waveTime.decrementAndGet(); // Zieht 1 ab und gibt Rest zurück
                Universe.get().sendMessage(Message.raw("Test mutable: " + current));
            });
        }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        countdownDisplay.startCountdown(30, playerRef, false);

//        while (getCorruption() < 10){
//            CompletableFuture.runAsync(() -> {
//
//            }, CompletableFuture.delayedExecutor(111, TimeUnit.SECONDS));
//        }

        var removeAfterSeconds = 30;
        removeNPCs(removeAfterSeconds);
//        PrefabPathHelper. //there is no remove function
    }

    private void setupNPCInventory(Ref<EntityStore> npcRef, Store<EntityStore> store) {
        NPCEntity npcComponent = store.getComponent(npcRef, Objects.requireNonNull(NPCEntity.getComponentType()));

// Initialize inventory size (e.g., 3 rows, 9 columns, 0 offset)
//        npcComponent.setInventorySize(3, 9, 0);

        Inventory inventory = npcComponent.getInventory();

// Add a Thorium Mace to the first slot of the hotbar
//        inventory.getHotbar().addItemStackToSlot((short) 0, new ItemStack("Weapon_Mace_Thorium", 1));

// Equip a Thorium Helmet using the InventoryHelper
//        InventoryHelper.useArmor(inventory.getArmor(), "Armor_Thorium_Head");

//        Ref<EntityStore> target1 = target.key();
//        npcComponent.onFlockSetTarget("LockedTarget", target1);
        //fix by commenting - because i have commented the Crocodile because didn't work with setting LocketTarget
        //and was getting annoying that it spawned a Crocodile each use/interact f
        //[2026/04/03 17:32:58 SEVERE]                  [World|default] Failed to run task!
        //java.lang.NullPointerException: Cannot invoke "it.unimi.dsi.fastutil.Pair.key()" because "this.target" is null
        //	at com.arkatale.defenseplugin.logic.WaveManager.setupNPCInventory(WaveManager.java:176)
        //	at com.arkatale.defenseplugin.logic.WaveManager.lambda$spawnWave$0(WaveManager.java:129)
//        npcComponent.setLeashPoint(toDefendPosition.toVector3d()); //hat sich nicht dahin bewegt - schade
        //aber gleichzeitig gut weil sonst hätte 6 tage wo ich npc probiert habe / tutorial von hytalemodding.dev
        //verschwendet

//        npcComponent.
//        npcComponent.setAppearance(npcRef, "Skeleton_Archmage", );
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

    public int getCorruption() {
        return corruption;
    }

    public void setCorruption(int setCorruption) {
        var newCorruption = this.corruption + setCorruption;

        if(newCorruption <= 0){
            newCorruption = 0;
        }

        this.corruption = newCorruption;
    }
}