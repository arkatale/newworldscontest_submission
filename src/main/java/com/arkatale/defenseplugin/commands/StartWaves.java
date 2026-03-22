package com.arkatale.defenseplugin.commands;

import com.arkatale.defenseplugin.logic.DefendSession;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.arkatale.defenseplugin.logic.WaveManager;
import org.jspecify.annotations.NonNull;

public class StartWaves extends AbstractPlayerCommand {

    private DefendSession defendSession;

    public StartWaves(DefendSession defendSession) {
        super("waves", "Start waves to defend the block");
        this.defendSession = defendSession;
    }

    @Override
    protected void execute(@NonNull CommandContext commandContext, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        var playerTransform = store.getComponent(ref, TransformComponent.getComponentType());
//        waveManager.startWaves(playerTransform.getPosition().toVector3i(), world);
        var placeholderPositionToDefend = playerTransform.getPosition().toVector3i();
//        waveSessions.add(new WaveManager(placeholderPositionToDefend));
        defendSession = new DefendSession(placeholderPositionToDefend, new WaveManager(placeholderPositionToDefend));
    }
}
