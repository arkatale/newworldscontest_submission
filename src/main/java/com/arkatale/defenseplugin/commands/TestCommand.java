package com.arkatale.defenseplugin.commands;

import com.arkatale.defenseplugin.DefensePlugin;
import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class TestCommand extends AbstractPlayerCommand {

    public TestCommand() {
        super("test", "");
    }

    @Override
    protected void execute(@NonNull CommandContext commandContext, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
//BuilderToolsPlugin.BuilderState.addToQueue
        BlockSelection blockSelection = new BlockSelection();
        var player = store.getComponent(ref, Player.getComponentType());
        player.getPlayerConnection().write(((BlockSelection) Objects.requireNonNullElseGet(blockSelection, BlockSelection::new)).toPacketWithSelection());
//        not showing up
    }
}
