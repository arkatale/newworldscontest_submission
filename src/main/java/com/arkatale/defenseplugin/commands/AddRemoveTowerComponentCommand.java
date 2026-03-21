package com.arkatale.defenseplugin.commands;

import com.arkatale.defenseplugin.component.TowerComponent;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

public class AddRemoveTowerComponentCommand extends AbstractPlayerCommand {
    public AddRemoveTowerComponentCommand() {
        super("tower", "description");
    }

    @Override
    protected void execute(@NonNull CommandContext commandContext, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        var comp = store.getComponent(ref, TowerComponent.getComponentType());

        if(comp == null){
        store.ensureComponent(ref, TowerComponent.getComponentType());


        }else   {
            store.removeComponent(ref, TowerComponent.getComponentType());

        }
    }
}
