package com.arkatale.defenseplugin;

import com.arkatale.defenseplugin.commands.AddRemoveTowerComponentCommand;
import com.arkatale.defenseplugin.commands.StartWaves;
import com.arkatale.defenseplugin.components.TowerComponent;
import com.arkatale.defenseplugin.events.ExampleEvent;
import com.arkatale.defenseplugin.logic.DefendSession;
import com.arkatale.defenseplugin.systems.TowerTickingSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.arkatale.defenseplugin.logic.WaveManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefensePlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private WaveManager waveManager;
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


        DefendSession defendSession = null;
        this.getCommandRegistry().registerCommand(new StartWaves(defendSession));
//        this.getEventRegistry().register(new WaveStartListener(waveManager));

    }

    @Override
    protected void start() {
        this.getEntityStoreRegistry().registerSystem(new TowerTickingSystem(5));
    }


}
