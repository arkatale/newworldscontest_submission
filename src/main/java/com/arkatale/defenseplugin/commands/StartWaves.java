package com.arkatale.defenseplugin.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import defence.WaveManager;
import org.jspecify.annotations.NonNull;

public class StartWaves extends AbstractPlayerCommand {
    private final WaveManager waveManager;

    public StartWaves(WaveManager waveManager) {
        super("waves", "Start waves to defend the block");
        this.waveManager = waveManager;
    }

    @Override
    protected void execute(@NonNull CommandContext commandContext, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        var playerTransform = store.getComponent(ref, TransformComponent.getComponentType());
        waveManager.startWaves(playerTransform.getPosition().toVector3i());
    }
}
