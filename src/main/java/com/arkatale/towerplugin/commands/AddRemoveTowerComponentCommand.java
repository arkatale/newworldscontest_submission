package com.arkatale.towerplugin.commands;

import com.arkatale.towerplugin.component.TowerComponent;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
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
//        Component<EntityStore> towerComponent = new TowerComponent();
//        store.addComponent(playerRef, TowerComponent.getComponentType());
//        Transform transform = new Transform(
//                new Vector3d(1, 1, 1),      // Position
//                new Rotation(1, pitch, 0) // Rotation, last value seems to always be 0
//        );
//        Teleport teleport = Teleport.createForPlayer(world, transform);
//        store.addComponent(ref, Teleport.getComponentType(), teleport);
        var transform = playerRef.getTransform();
        var pos = transform.getPosition();
        transform.setPosition(pos.clone().add(0,2,0));
        Teleport teleport = Teleport.createForPlayer(world, transform);
        store.addComponent(ref, Teleport.getComponentType(), teleport);

        store.ensureComponent(ref, TowerComponent.getComponentType());
    }
}
