package com.arkatale.defenseplugin;

import com.arkatale.defenseplugin.commands.AddRemoveTowerComponentCommand;
import com.arkatale.defenseplugin.commands.RemoveAllCoreSiegePatrolEntities;
import com.arkatale.defenseplugin.commands.StartWaves;
import com.arkatale.defenseplugin.commands.TestCommand;
import com.arkatale.defenseplugin.components.AttackerComponent;
import com.arkatale.defenseplugin.components.DefendBlockComponent;
import com.arkatale.defenseplugin.components.TowerComponent;
import com.arkatale.defenseplugin.events.ExampleEvent;
import com.arkatale.defenseplugin.events.WaveStartListener;
import com.arkatale.defenseplugin.logic.DefendSession;
import com.arkatale.defenseplugin.systems.DefendBlockSystem;
import com.arkatale.defenseplugin.systems.TowerTickingSystem;
import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefensePlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final Map<Vector3i, DefendSession> activeSessions = new ConcurrentHashMap<>();

    public DefensePlugin(JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from %s version %s", this.getName(), this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
//        waveManager = new WaveManager();

        var at_towerComponentType = this.getEntityStoreRegistry().registerComponent(TowerComponent.class, "AT_TowerComponent", TowerComponent.CODEC);

        TowerComponent.setComponentType(at_towerComponentType);

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);

        this.getCommandRegistry().registerCommand(new AddRemoveTowerComponentCommand());

        var at_attackerComponent = this.getEntityStoreRegistry().registerComponent(AttackerComponent.class, "AT_AttackerComponent", AttackerComponent.CODEC);
        AttackerComponent.setComponentType(at_attackerComponent);

        DefendSession defendSession = null;
//        this.getCommandRegistry().registerCommand(new StartWaves(DefendSession.checkAndStartDefendSession()));
//        this.getCommandRegistry().registerCommand(new StartWaves(DefendSession::checkAndStartDefendSession));
//        this.getEventRegistry().register(new WaveStartListener(waveManager));
        var cmdRegistry = this.getCommandRegistry();
        cmdRegistry.registerCommand(new StartWaves(this));

        var at_DefendBlockComponent = this.getChunkStoreRegistry().registerComponent(DefendBlockComponent.class, "AT_DefendBlockComponent", DefendBlockComponent.CODEC);

        DefendBlockComponent.setComponentType(at_DefendBlockComponent);

//        this.getEventRegistry().register(new WaveStartListener(this));
        this.getEntityStoreRegistry().registerSystem(new WaveStartListener(this));

        this.getCommandRegistry().registerCommand(new RemoveAllCoreSiegePatrolEntities());
        this.getCommandRegistry().registerCommand(new TestCommand());
    }

    @Override
    protected void start() {
        this.getEntityStoreRegistry().registerSystem(new TowerTickingSystem(5));
//        this.getEntityStoreRegistry().registerSystem(new AttackerSystem());
        this.getChunkStoreRegistry().registerSystem(new DefendBlockSystem());
    }

    public DefendSession startDefenseAt(Vector3i defendPos, World world, EntityStore store, PatrolPathMarkerEntity target, Player startingPlayer){
//        if(activeSessions.containsKey(defendPos)){
//            return null;
//        }

        DefendSession defendSession = new DefendSession(defendPos, startingPlayer, defendPos, world, store, target);
        activeSessions.put(defendPos, defendSession);

        defendSession.getWaveManager().runSingleWave(1);
        return defendSession;
    }
}
